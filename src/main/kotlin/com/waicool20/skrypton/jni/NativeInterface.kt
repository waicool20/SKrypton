package com.waicool20.skrypton.jni

data class CPointer(val handle: Long)

abstract class NativeInterface: AutoCloseable {
    abstract val handle: CPointer
    fun dispose() = close()
}
