package com.waicool20.skrypton

import com.waicool20.skrypton.enums.MouseEventType
import com.waicool20.skrypton.jni.objects.SKryptonApp
import com.waicool20.skrypton.jni.objects.SKryptonWebView
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val app = SKryptonApp.initalize(remoteDebugPort = 8888)
    val webView = SKryptonWebView("https://www.google.com")
    webView.resize(1280, 720)
    webView.show()
    webView.addOnMouseEventListener(MouseEventType.MouseMove) {
        println("Move ${it.x} ${it.y}")
    }
    thread {
        TimeUnit.SECONDS.sleep(2)
        println("Loading GitHub")
        webView.load("https://github.com/")
    }
    thread {
        TimeUnit.SECONDS.sleep(10)
        webView.back()
        app.runOnMainThread {
            //webView.dispose()
        }
    }
    exitProcess(app.exec())
}
