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




