package com.waicool20.skrypton

import com.waicool20.skrypton.jni.objects.SKryptonApp
import com.waicool20.skrypton.jni.objects.SKryptonWebView
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val app = SKryptonApp.initalize()
    val webView = SKryptonWebView.createNew("https://www.google.com")
    //val webView1 = SKryptonWebView.createNew("https://www.google.com")
    var i = 0
    thread {
        TimeUnit.SECONDS.sleep(2)
        app.runOnMainThread(Runnable {
            println("Hello")
            i = 10
            webView.dispose()
            println(i)
        })
    }
    exitProcess(app.exec())
}
