package com.waicool20.skrypton

import com.waicool20.skrypton.enums.MouseButton
import com.waicool20.skrypton.enums.MouseEventType
import com.waicool20.skrypton.jni.objects.SKryptonApp
import com.waicool20.skrypton.jni.objects.SKryptonMouseEvent
import com.waicool20.skrypton.jni.objects.SKryptonWebView
import java.awt.Point
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val app = SKryptonApp.initalize(remoteDebugPort = 8888)
    val webView = SKryptonWebView("https://www.google.com")
    webView.resize(1280, 720)
    webView.show()
    thread {
        TimeUnit.SECONDS.sleep(2)
        println(webView.url)
        println("Loading GitHub")
        webView.load("https://github.com/")
    }
    thread {
        TimeUnit.SECONDS.sleep(5)
        //webView.back()

        println("Sending events!")
        repeat(1) {
            webView.sendEvent(SKryptonMouseEvent(MouseEventType.MouseMove, Point(670, 400)))
            webView.sendEvent(SKryptonMouseEvent(MouseEventType.MouseButtonPress, Point(670, 400), button = MouseButton.LeftButton))
            webView.sendEvent(SKryptonMouseEvent(MouseEventType.MouseButtonRelease, Point(670, 400), button = MouseButton.LeftButton))
        }
        println(webView.url)
    }
    exitProcess(app.exec())
}
