package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import com.waicool20.skrypton.jni.NativeInterface
import com.waicool20.skrypton.util.SystemUtils
import java.nio.file.Paths

object SKryptonApp : NativeInterface() {
    override lateinit var handle: CPointer

    init {
        val path = Paths.get("").normalize().toAbsolutePath().resolve("native/build/libSKryptonNative.so")
        println("Loading library at $path")
        SystemUtils.loadLibrary(path)
    }

    fun initalize(args: Array<String> = emptyArray()): SKryptonApp {
        handle = CPointer(initialize_N(args))
        println("Initialized at $handle")
        return this
    }

    fun exec() = exec_N()

    override fun close() {
        destroy_N()
    }

    private external fun initialize_N(args: Array<String>): Long
    private external fun exec_N(): Int
    private external fun destroy_N()
}
