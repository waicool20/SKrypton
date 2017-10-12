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

package com.waicool20.skrypton.jni

import com.waicool20.skrypton.util.loggerFor

/**
 * Represents a native pointer.
 *
 * @property value Holds the numerical value of the native pointer.
 * @constructor Main constructor
 * @param value Numerical value of the native pointer.
 */
data class CPointer(val value: Long) {
    private val logger = loggerFor<CPointer>()

    init {
        logger.trace { "New $this" }
    }
}

/**
 * Base class for classes that have native components.
 */
abstract class NativeInterface : AutoCloseable {
    /**
     * Native pointer
     */
    abstract val handle: CPointer

    /**
     * Disposes this object, usually same action as [close]
     */
    fun dispose() = close()
}
