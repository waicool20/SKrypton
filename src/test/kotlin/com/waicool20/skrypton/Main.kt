/*
 * The MIT License (MIT)
 *
 * Copyright (c) SKrypton by waicool20
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.waicool20.skrypton

import com.waicool20.skrypton.jni.objects.SKryptonApp
import com.waicool20.skrypton.sikulix.screen
import org.sikuli.script.ImagePath
import org.sikuli.script.Location
import java.awt.Rectangle
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    SKryptonApp.initialize(args, remoteDebugPort = 8888) {
        screen("http://www.onemotion.com/flash/sketch-paint/") {
            webView.settings.pluginsEnabled = true
            ImagePath.add(ClassLoader.getSystemClassLoader().getResource("images"))
            TimeUnit.SECONDS.sleep(8)
            println("Sending events!")
            click("ok1.png")
            TimeUnit.SECONDS.sleep(1)
            click("ok2.png")
            TimeUnit.SECONDS.sleep(1)
            click("size.png")
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
                dragDrop(location1, location2)
            }
        }
    }.exec(true)
}
