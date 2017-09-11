package com.waicool20.skrypton

import com.waicool20.skrypton.jni.objects.SKryptonApp
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val app = SKryptonApp.initalize()
    exitProcess(app.exec())
}
