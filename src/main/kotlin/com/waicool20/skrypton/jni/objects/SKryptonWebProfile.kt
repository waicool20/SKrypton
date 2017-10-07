/*
 * The MIT License (MIT)
 *
 * Copyright (c) skrypton-api by waicool20
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

package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import com.waicool20.skrypton.jni.NativeInterface
import com.waicool20.skrypton.util.loggerFor
import java.nio.file.Path
import java.nio.file.Paths

class SKryptonWebProfile private constructor(pointer: Long) : NativeInterface() {
    private val logger = loggerFor<SKryptonWebProfile>()
    override val handle = CPointer(pointer)

    companion object {
        val defaults by lazy { defaultProfile_N() }
        private external fun defaultProfile_N(): SKryptonWebProfile
    }

    var cachePath: Path
        get() = Paths.get(getCachePath_N())
        set(value) = setCachePath_N(value.toString())

    //<editor-fold desc="Native functions">

    private external fun getCachePath_N(): String
    private external fun setCachePath_N(path: String)

    //</editor-fold>
    override fun close() {
        logger.error { "Profile cannot be disposed, dispose related WebView instead" }
    }
}
