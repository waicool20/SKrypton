package com.waicool20.skrypton

import com.waicool20.skrypton.jni.objects.SKryptonApp
import com.waicool20.skrypton.jni.objects.SKryptonWebView
import com.waicool20.skrypton.sikulix.SKryptonScreen
import org.sikuli.script.Location
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val app = SKryptonApp.initalize(remoteDebugPort = 8888)
    val webView = SKryptonWebView("https://www.google.com")
    webView.resize(1280, 720)
    webView.show()
    webView.showCursor = true
    val screen = SKryptonScreen(webView)
    thread {
        TimeUnit.SECONDS.sleep(2)
        println(webView.url)
    }
    thread {
        TimeUnit.SECONDS.sleep(5)
        println("Sending events!")
        screen.click(Location(1000, 600))
    }
    exitProcess(app.exec())
}
