package com.waicool20.skrypton

import com.waicool20.skrypton.jni.objects.SKryptonApp
import com.waicool20.skrypton.jni.objects.SKryptonWebView
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val app = SKryptonApp.initalize()
    val webView = SKryptonWebView.createNew("https://www.google.com")
    exitProcess(app.exec())
}
