/*
 * Copyright 2011-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lambdaworks.redis.cluster;

import java.util.ArrayList;
import java.util.Collection;

import com.lambdaworks.redis.ClientOptions;
import com.lambdaworks.redis.RedisChannelWriter;
import com.lambdaworks.redis.RedisException;
import com.lambdaworks.redis.protocol.ConnectionWatchdog;
import com.lambdaworks.redis.protocol.RedisCommand;
import com.lambdaworks.redis.protocol.DefaultEndpoint;
import com.lambdaworks.redis.resource.ClientResources;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Command handler for node connections within the Redis Cluster context. This handler can requeue commands if it is
 * disconnected and closed but has commands in the queue. If the handler was connected it would retry commands using the
 * {@literal MOVED} or {@literal ASK} redirection.
 *
 * @author Mark Paluch
 */
class ClusterNodeEndpoint extends DefaultEndpoint {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ClusterNodeEndpoint.class);

    private final RedisChannelWriter clusterChannelWriter;

    /**
     * Initialize a new instance that handles commands from the supplied queue.
     *
     * @param clientOptions client options for this connection
     * @param clientResources client resources for this connection
     * @param clusterChannelWriter top-most channel writer.
     */
    public ClusterNodeEndpoint(ClientOptions clientOptions, ClientResources clientResources,
            RedisChannelWriter clusterChannelWriter) {
        super(clientOptions);
        this.clusterChannelWriter = clusterChannelWriter;
    }

    /**
     * Prepare the closing of the channel.
     */
    public void prepareClose() {
        if (channel != null) {
            ConnectionWatchdog connectionWatchdog = channel.pipeline().get(ConnectionWatchdog.class);
            if (connectionWatchdog != null) {
                connectionWatchdog.setReconnectSuspended(true);
            }
        }
    }

    /**
     * Move queued and buffered commands from the inactive connection to the master command writer. This is done only if the
     * current connection is disconnected and auto-reconnect is enabled (command-retries). If the connection would be open, we
     * could get into a race that the commands we're moving are right now in processing. Alive connections can handle redirects
     * and retries on their own.
     */
    @Override
    public void close() {

        logger.debug("{} close()", logPrefix());

        if (clusterChannelWriter != null) {

            Collection<RedisCommand<?, ?, ?>> commands = shiftCommands(getQueue());
            retriggerCommands(commands);
        }

        super.close();
    }

    protected void retriggerCommands(Collection<RedisCommand<?, ?, ?>> commands) {

        for (RedisCommand<?, ?, ?> queuedCommand : commands) {

            if (queuedCommand == null || queuedCommand.isCancelled()) {
                continue;
            }

            try {
                clusterChannelWriter.write(queuedCommand);
            } catch (RedisException e) {
                queuedCommand.completeExceptionally(e);
            }
        }
    }

    /**
     * Retrieve commands within a lock to prevent concurrent modification
     */
    private Collection<RedisCommand<?, ?, ?>> shiftCommands(Collection<? extends RedisCommand<?, ?, ?>> source) {

        return doExclusive(() -> {
            try {
                return new ArrayList<>(source);
            } finally {
                source.clear();
            }
        });
    }

}
