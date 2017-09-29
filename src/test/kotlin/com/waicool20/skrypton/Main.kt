package com.waicool20.skrypton

import com.waicool20.skrypton.jni.objects.SKryptonApp
import com.waicool20.skrypton.jni.objects.SKryptonWebView
import com.waicool20.skrypton.sikulix.SKryptonScreen
import org.sikuli.script.ImagePath
import org.sikuli.script.Location
import java.awt.Rectangle
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    ImagePath.add(ClassLoader.getSystemClassLoader().getResource("images"))
    val app = SKryptonApp.initalize(remoteDebugPort = 8888)
    val webView = SKryptonWebView("http://www.onemotion.com/flash/sketch-paint/")
    webView.settings.pluginsEnabled = true
    webView.resize(1280, 720)
    webView.show()
    webView.showCursor = true
    val screen = SKryptonScreen(webView)
    thread {
        TimeUnit.SECONDS.sleep(3)
        println("Sending events!")
        screen.click("ok1.png")
        TimeUnit.SECONDS.sleep(1)
        screen.click("ok2.png")
        TimeUnit.SECONDS.sleep(1)
        screen.click("size.png")
        val rng = ThreadLocalRandom.current()
        val drawingArea = Rectangle(195, 65, 1020, 590)
        while (true) {
            val location1 = Location(
                    rng.nextInt(drawingArea.x, drawingArea.x + drawingArea.width),
                    rng.nextInt(drawingArea.y, drawingArea.y + drawingArea.height)
            )
            val location2 = Location(
                    rng.nextInt(drawingArea.x, drawingArea.x + drawingArea.width),
                    rng.nextInt(drawingArea.y, drawingArea.y + drawingArea.height)
            )
            println("Drawing line from $location1 to $location2")
            screen.dragDrop(location1, location2)
        }
    }
    exitProcess(app.exec())
}
