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
package com.lambdaworks.redis.cluster.api.async;

import java.util.Date;
import java.util.List;
import com.lambdaworks.redis.KillArgs;
import com.lambdaworks.redis.protocol.CommandType;
import com.lambdaworks.redis.RedisFuture;

/**
 * Asynchronous executed commands on a node selection for Server Control.
 * 
 * @param <K> Key type.
 * @param <V> Value type.
 * @author Mark Paluch
 * @since 4.0
 * @generated by com.lambdaworks.apigenerator.CreateAsyncNodeSelectionClusterApi
 */
public interface NodeSelectionServerAsyncCommands<K, V> {

    /**
     * Asynchronously rewrite the append-only file.
     * 
     * @return String simple-string-reply always {@code OK}.
     */
    AsyncExecutions<String> bgrewriteaof();

    /**
     * Asynchronously save the dataset to disk.
     * 
     * @return String simple-string-reply
     */
    AsyncExecutions<String> bgsave();

    /**
     * Get the current connection name.
     * 
     * @return K bulk-string-reply The connection name, or a null bulk reply if no name is set.
     */
    AsyncExecutions<K> clientGetname();

    /**
     * Set the current connection name.
     * 
     * @param name the client name
     * @return simple-string-reply {@code OK} if the connection name was successfully set.
     */
    AsyncExecutions<String> clientSetname(K name);

    /**
     * Kill the connection of a client identified by ip:port.
     * 
     * @param addr ip:port
     * @return String simple-string-reply {@code OK} if the connection exists and has been closed
     */
    AsyncExecutions<String> clientKill(String addr);

    /**
     * Kill connections of clients which are filtered by {@code killArgs}
     *
     * @param killArgs args for the kill operation
     * @return Long integer-reply number of killed connections
     */
    AsyncExecutions<Long> clientKill(KillArgs killArgs);

    /**
     * Stop processing commands from clients for some time.
     * 
     * @param timeout the timeout value in milliseconds
     * @return String simple-string-reply The command returns OK or an error if the timeout is invalid.
     */
    AsyncExecutions<String> clientPause(long timeout);

    /**
     * Get the list of client connections.
     * 
     * @return String bulk-string-reply a unique string, formatted as follows: One client connection per line (separated by LF),
     *         each line is composed of a succession of property=value fields separated by a space character.
     */
    AsyncExecutions<String> clientList();

    /**
     * Returns an array reply of details about all Redis commands.
     * 
     * @return List&lt;Object&gt; array-reply
     */
    AsyncExecutions<List<Object>> command();

    /**
     * Returns an array reply of details about the requested commands.
     * 
     * @param commands the commands to query for
     * @return List&lt;Object&gt; array-reply
     */
    AsyncExecutions<List<Object>> commandInfo(String... commands);

    /**
     * Returns an array reply of details about the requested commands.
     * 
     * @param commands the commands to query for
     * @return List&lt;Object&gt; array-reply
     */
    AsyncExecutions<List<Object>> commandInfo(CommandType... commands);

    /**
     * Get total number of Redis commands.
     * 
     * @return Long integer-reply of number of total commands in this Redis server.
     */
    AsyncExecutions<Long> commandCount();

    /**
     * Get the value of a configuration parameter.
     * 
     * @param parameter name of the parameter
     * @return List&lt;String&gt; bulk-string-reply
     */
    AsyncExecutions<List<String>> configGet(String parameter);

    /**
     * Reset the stats returned by INFO.
     * 
     * @return String simple-string-reply always {@code OK}.
     */
    AsyncExecutions<String> configResetstat();

    /**
     * Rewrite the configuration file with the in memory configuration.
     * 
     * @return String simple-string-reply {@code OK} when the configuration was rewritten properly. Otherwise an error is
     *         returned.
     */
    AsyncExecutions<String> configRewrite();

    /**
     * Set a configuration parameter to the given value.
     * 
     * @param parameter the parameter name
     * @param value the parameter value
     * @return String simple-string-reply: {@code OK} when the configuration was set properly. Otherwise an error is returned.
     */
    AsyncExecutions<String> configSet(String parameter, String value);

    /**
     * Return the number of keys in the selected database.
     * 
     * @return Long integer-reply
     */
    AsyncExecutions<Long> dbsize();

    /**
     * Crash and recover
     * @param delay optional delay in milliseconds
     * @return String simple-string-reply
     */
    AsyncExecutions<String> debugCrashAndRecover(Long delay);

    /**
     * Get debugging information about the internal hash-table state.
     *
     * @param db the database number
     * @return String simple-string-reply
     */
    AsyncExecutions<String> debugHtstats(int db);

    /**
     * Get debugging information about a key.
     * 
     * @param key the key
     * @return String simple-string-reply
     */
    AsyncExecutions<String> debugObject(K key);

    /**
     * Save RDB, clear the database and reload RDB.
     *
     * @return String simple-string-reply The commands returns OK on success.
     */
    AsyncExecutions<String> debugReload();

    /**
     * Restart the server gracefully.
     * @param delay optional delay in milliseconds
     * @return String simple-string-reply
     */
    AsyncExecutions<String> debugRestart(Long delay);

    /**
     * Get debugging information about the internal SDS length.
     *
     * @param key the key
     * @return String simple-string-reply
     */
    AsyncExecutions<String> debugSdslen(K key);

    /**
     * Remove all keys from all databases.
     * 
     * @return String simple-string-reply
     */
    AsyncExecutions<String> flushall();

    /**
     * Remove all keys asynchronously from all databases.
     *
     * @return String simple-string-reply
     */
    AsyncExecutions<String> flushallAsync();

    /**
     * Remove all keys from the current database.
     * 
     * @return String simple-string-reply
     */
    AsyncExecutions<String> flushdb();

    /**
     * Remove all keys asynchronously from the current database.
     *
     * @return String simple-string-reply
     */
    AsyncExecutions<String> flushdbAsync();

    /**
     * Get information and statistics about the server.
     * 
     * @return String bulk-string-reply as a collection of text lines.
     */
    AsyncExecutions<String> info();

    /**
     * Get information and statistics about the server.
     * 
     * @param section the section type: string
     * @return String bulk-string-reply as a collection of text lines.
     */
    AsyncExecutions<String> info(String section);

    /**
     * Get the UNIX time stamp of the last successful save to disk.
     * 
     * @return Date integer-reply an UNIX time stamp.
     */
    AsyncExecutions<Date> lastsave();

    /**
     * Synchronously save the dataset to disk.
     * 
     * @return String simple-string-reply The commands returns OK on success.
     */
    AsyncExecutions<String> save();

    /**
     * Make the server a slave of another instance, or promote it as master.
     * 
     * @param host the host type: string
     * @param port the port type: string
     * @return String simple-string-reply
     */
    AsyncExecutions<String> slaveof(String host, int port);

    /**
     * Promote server as master.
     * 
     * @return String simple-string-reply
     */
    AsyncExecutions<String> slaveofNoOne();

    /**
     * Read the slow log.
     * 
     * @return List&lt;Object&gt; deeply nested multi bulk replies
     */
    AsyncExecutions<List<Object>> slowlogGet();

    /**
     * Read the slow log.
     * 
     * @param count the count
     * @return List&lt;Object&gt; deeply nested multi bulk replies
     */
    AsyncExecutions<List<Object>> slowlogGet(int count);

    /**
     * Obtaining the current length of the slow log.
     * 
     * @return Long length of the slow log.
     */
    AsyncExecutions<Long> slowlogLen();

    /**
     * Resetting the slow log.
     * 
     * @return String simple-string-reply The commands returns OK on success.
     */
    AsyncExecutions<String> slowlogReset();

    /**
     * Return the current server time.
     * 
     * @return List&lt;V&gt; array-reply specifically:
     * 
     *         A multi bulk reply containing two elements:
     * 
     *         unix time in seconds. microseconds.
     */
    AsyncExecutions<List<V>> time();
}
