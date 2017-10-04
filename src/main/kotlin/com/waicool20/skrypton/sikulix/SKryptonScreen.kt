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

package com.waicool20.skrypton.sikulix

import com.waicool20.skrypton.enums.MouseEventType
import com.waicool20.skrypton.jni.objects.*
import com.waicool20.skrypton.sikulix.input.SKryptonKeyboard
import com.waicool20.skrypton.sikulix.input.SKryptonMouse
import com.waicool20.skrypton.sikulix.input.SKryptonRobot
import com.waicool20.skrypton.util.loggerFor
import org.sikuli.basics.Settings
import org.sikuli.script.*
import java.awt.Point
import java.awt.Rectangle
import java.util.concurrent.CountDownLatch

class SKryptonScreen(val webView: SKryptonWebView) : SKryptonRegion(0, 0, webView.size.width, webView.size.height), IScreen {
    private val logger = loggerFor<SKryptonScreen>()
    private val robot = SKryptonRobot(this)
    val mouse = SKryptonMouse(robot)
    val keyboard = SKryptonKeyboard(robot)
    var clipboard = ""

    init {
        isVirtual = true
        setOtherScreen(this)
        webView.addOnResizeEventListener {
            setSize(it.newSize.width, it.newSize.height)
        }
    }

    override fun showTarget(location: Location) {
        val width = 50
        val height = 50
        val x = location.x - width / 2
        val y = location.y - height / 2
        WebViewHighlighter(webView, x, y, width, height).showForAndDispose(Settings.SlowMotionDelay)
    }

    override fun getIdFromPoint(srcx: Int, srcy: Int): Int = 0

    override fun getRobot(): IRobot = robot

    override fun userCapture(string: String?): ScreenImage {
        val message = string ?:
                """
                After you click OK, the first click selects the top left corner and the
                second click selects the bottom right corner of the screenshot
                """.trimIndent().replace("\n", "\\n")

        webView.runJavaScript("alert('$message');")

        var point1: Point? = null
        var point2: Point? = null

        val screenShotLatch = CountDownLatch(2)
        val highlighter = WebViewHighlighter(webView, 0, 0, 10, 10)
        val listener: (SKryptonMouseEvent) -> Unit = {
            if (point1 == null) {
                point1 = it.localPos
                highlighter.move(it.x, it.y)
                highlighter.show()
                logger.debug("Screenshot position 1 set (X: ${it.x} Y: ${it.y})")
                screenShotLatch.countDown()
            } else if (point2 == null) {
                if (it.x > point1?.x ?: 0 && it.y > point1?.y ?: 0) {
                    point2 = it.localPos
                    logger.debug("Screenshot position 2 set (X: ${it.x} Y: ${it.y})")
                    screenShotLatch.countDown()
                }
            }
        }

        val moveListener: (SKryptonMouseEvent) -> Unit = {
            point1?.let { p ->
                if (it.x > p.x && it.y > p.y) {
                    highlighter.resize(it.x - p.x, it.y - p.y)
                }
            }
        }
        webView.addOnMouseEventListener(MouseEventType.MouseButtonPress, listener)
        webView.addOnMouseEventListener(MouseEventType.MouseMove, moveListener)
        screenShotLatch.await()
        webView.removeOnMouseEventListener(MouseEventType.MouseButtonPress, listener)
        webView.removeOnMouseEventListener(MouseEventType.MouseMove, moveListener)

        val p1 = requireNotNull(point1)
        val p2 = requireNotNull(point2)

        val width = p2.x - p1.x
        val height = p2.y - p1.y

        require(width > 0) { "Selection invalid: Negative width" }
        require(height > 0) { "Selection invalid: Negative height" }

        val image = capture(p1.x, p1.y, width, height)
        webView.runJavaScript("alert('Screenshot taken!');") {
            highlighter.close()
        }
        return image
    }

    override fun getID(): Int = webView.handle.value.toInt()

    override fun getBounds(): Rectangle = webView.geometry

    override fun capture(): ScreenImage = capture(rect)
    override fun capture(region: Region): ScreenImage = capture(region.x, region.y, region.w, region.h)
    override fun capture(x: Int, y: Int, width: Int, height: Int): ScreenImage = capture(Rectangle(x, y, width, height))
    override fun capture(rect: Rectangle): ScreenImage = with(webView.takeScreenshot()) {
        ScreenImage(Rectangle(0, 0, this.width, this.height), this).getSub(rect)
    }

    override fun getLastScreenImageFromScreen(): ScreenImage? = lastScreenImage

    fun currentMousePosition() = Location(webView.cursorX, webView.cursorY)
}

fun SKryptonApp.screen(url: String,
                       showCursor: Boolean = true,
                       width: Int = 1280,
                       height: Int = 720,
                       action: SKryptonScreen.() -> Unit = {}
): SKryptonScreen {
    val webView = SKryptonWebView(url).also {
        it.showCursor = showCursor
        it.resize(width, height)
        it.show()
    }
    return webView.screen { action() }
}

fun SKryptonWebView.screen(action: SKryptonScreen.() -> Unit = {}): SKryptonScreen {
    return SKryptonScreen(this).apply { action() }
}
