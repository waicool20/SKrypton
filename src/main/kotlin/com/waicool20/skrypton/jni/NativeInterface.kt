package com.waicool20.skrypton.jni

import com.waicool20.skrypton.util.loggerFor

data class CPointer(val value: Long) {
    private val logger = loggerFor<CPointer>()

    init {
        logger.trace { "New $this" }
    }
}

abstract class NativeInterface : AutoCloseable {
    abstract val handle: CPointer
    fun dispose() = close()
}
