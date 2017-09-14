package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import com.waicool20.skrypton.jni.NativeInterface
import com.waicool20.skrypton.util.SystemUtils
import com.waicool20.skrypton.util.loggerFor
import java.nio.file.Paths

object SKryptonApp : NativeInterface() {
    private val logger = loggerFor<SKryptonApp>()
    override lateinit var handle: CPointer

    init {
        val path = Paths.get("").normalize().toAbsolutePath().resolve("native/build/libSKryptonNative.so")
        logger.debug { "Loading library at $path" }
        SystemUtils.loadLibrary(path)
    }

    fun initalize(args: Array<String> = emptyArray()): SKryptonApp {
        handle = CPointer(initialize_N(args))
        return this
    }

    fun exec() = exec_N()
    fun runOnMainThread(action: Runnable) = runOnMainThread_N(action)

    override fun close() {
        dispose_N()
    }

    private external fun runOnMainThread_N(action: Runnable)
    private external fun initialize_N(args: Array<String>): Long
    private external fun exec_N(): Int
    private external fun dispose_N()
}
