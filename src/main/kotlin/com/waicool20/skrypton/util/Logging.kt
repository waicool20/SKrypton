/*
 * The MIT License (MIT)
 *
 * Copyright (c) SKrypton by waicool20
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.waicool20.skrypton.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Gets a [KLogger] instance for a given class.
 */
inline fun <reified T> loggerFor(): KLogger = KLogger(LoggerFactory.getLogger(T::class.java))

/**
 * Class that wraps a [Logger] instance, and adds logging functions which receive lambdas as their
 * argument. These functions are lazily evaluated.
 *
 * @property logger [Logger] instance to wrap.
 * @constructor Main constructor
 * @param logger [Logger] instance to wrap.
 */
class KLogger(val logger: Logger) {
    /**
     * Just like [Logger.info]
     *
     * @param msg Message to log
     */
    fun info(msg: String) = info { msg }

    /**
     * Executes given lambda and logs its result with [Logger.info].
     * Allows lazy evaluation of logging message.
     *
     * @param msg Lambda to execute
     */
    fun info(msg: () -> Any) = logger.info(msg().toString())

    /**
     * Just like [Logger.debug]
     *
     * @param msg Message to log
     */
    fun debug(msg: String) = debug { msg }

    /**
     * Executes given lambda and logs its result with [Logger.debug].
     * Allows lazy evaluation of logging message.
     *
     * @param msg Lambda to execute
     */
    fun debug(msg: () -> Any) = logger.debug(msg().toString())

    /**
     * Just like [Logger.warn]
     *
     * @param msg Message to log
     */
    fun warn(msg: String) = warn { msg }

    /**
     * Executes given lambda and logs its result with [Logger.warn].
     * Allows lazy evaluation of logging message.
     *
     * @param msg Lambda to execute
     */
    fun warn(msg: () -> Any) = logger.warn(msg().toString())

    /**
     * Just like [Logger.error]
     *
     * @param msg Message to log
     */
    fun error(msg: String) = error { msg }

    /**
     * Executes given lambda and logs its result with [Logger.error].
     * Allows lazy evaluation of logging message.
     *
     * @param msg Lambda to execute
     */
    fun error(msg: () -> Any) = logger.error(msg().toString())

    /**
     * Just like [Logger.trace]
     *
     * @param msg Message to log
     */
    fun trace(msg: String) = trace { msg }

    /**
     * Executes given lambda and logs its result with [Logger.trace].
     * Allows lazy evaluation of logging message.
     *
     * @param msg Lambda to execute
     */
    fun trace(msg: () -> Any) = logger.trace(msg().toString())
}




