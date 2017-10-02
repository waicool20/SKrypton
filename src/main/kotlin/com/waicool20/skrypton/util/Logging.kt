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

inline fun <reified T> loggerFor(): KLogger = KLogger(LoggerFactory.getLogger(T::class.java))

class KLogger(val logger: Logger) {
    fun info(msg: String) = info { msg }
    fun info(msg: () -> Any) = logger.info(msg().toString())

    fun debug(msg: String) = debug { msg }
    fun debug(msg: () -> Any) = logger.debug(msg().toString())

    fun warn(msg: String) = warn { msg }
    fun warn(msg: () -> Any) = logger.warn(msg().toString())

    fun error(msg: String) = error { msg }
    fun error(msg: () -> Any) = logger.error(msg().toString())

    fun trace(msg: String) = trace { msg }
    fun trace(msg: () -> Any) = logger.trace(msg().toString())
}




