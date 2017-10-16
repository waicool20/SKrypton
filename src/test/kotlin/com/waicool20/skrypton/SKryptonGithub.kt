/*
 * The MIT License (MIT)
 *
 * Copyright (c) skrypton-api by waicool20
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
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    SKryptonApp.initialize(args, remoteDebugPort = 8888) {
        screen("https://gist.github.com/") {
            ImagePath.add(ClassLoader.getSystemClassLoader().getResource("images"))
            TimeUnit.SECONDS.sleep(4)
            type(center, "SKrypton is a project that can automate your browser projects/test." +
                    "\nEverything you're seeing is being automated by SKrypton.\nIt can do more!")
            webView.runJavaScript("window.onbeforeunload = null;")
            TimeUnit.SECONDS.sleep(3)
            webView.load("https://github.com/waicool20/SKrypton")
            find("issues.png").click()
            TimeUnit.SECONDS.sleep(1)
            find("newissue.png").click()
            TimeUnit.SECONDS.sleep(1)
            type("SKrypton is awesome!")
            TimeUnit.SECONDS.sleep(3)
            find("cross.png").click()
            TimeUnit.SECONDS.sleep(1)
            find("star.png").apply {
                highlight("green")
                hover()
                while (true) {
                    hover(topLeft)
                    hover(bottomLeft)
                    hover(bottomRight)
                    hover(topRight)
                }
            }
        }
    }.exec(true)
}
