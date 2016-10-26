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
package com.lambdaworks.redis.protocol;

import java.util.Queue;

/**
 * Interface to be implemented by classes that queue commands. Implementors of this class need to expose their queue to control
 * all queues by maintenance and cleanup processes.
 *
 * @author Mark Paluch
 */
interface HasQueuedCommands {

    /**
     * The queue holding commands.
     * 
     * @return the queue
     */
    Queue<RedisCommand<?, ?, ?>> getQueue();
}
