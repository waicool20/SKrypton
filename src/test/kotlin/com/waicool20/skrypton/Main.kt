package com.waicool20.skrypton

import com.waicool20.skrypton.jni.objects.SKryptonApp
import com.waicool20.skrypton.jni.objects.SKryptonWebView
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val app = SKryptonApp.initalize()
    val webView = SKryptonWebView("https://www.google.com")
    webView.resize(1280, 720)
    webView.show()
    thread {
        TimeUnit.SECONDS.sleep(2)
        println("Loading GitHub")
        webView.load("https://www.github.com")
    }
    thread {
        TimeUnit.SECONDS.sleep(5)
        app.runOnMainThread {
            webView.dispose()
        }
    }
    exitProcess(app.exec())
}
