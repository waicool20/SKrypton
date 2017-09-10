package com.waicool20.skrypton.jni

import com.waicool20.skrypton.util.SystemUtils
import java.nio.file.Paths

class JNITest {
    init {
        val path = Paths.get("").normalize().toAbsolutePath().resolve("native/build/libSKryptonNative.so")
        println("Loading library at $path")
        SystemUtils.loadLibrary(path)
    }

    external fun say(string: String)
}
