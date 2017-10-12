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

/**
 * A class representing a [IScreen] that uses the given web view as its view port.
 *
 * @property webView The web view that the screen is using as its view port.
 * @constructor Main constructor
 * @param webView The web view that the screen is using as its view port.
 */
class SKryptonScreen(val webView: SKryptonWebView) : SKryptonRegion(0, 0, webView.size.width, webView.size.height), IScreen {
    private val logger = loggerFor<SKryptonScreen>()
    private val robot = SKryptonRobot(this)
    /**
     * Independent virtual mouse bound to this screen.
     */
    val mouse = SKryptonMouse(robot)
    /**
     * Independent virtual keyboard bound to this screen.
     */
    val keyboard = SKryptonKeyboard(robot)
    /**
     * Independent clipboard bound to this screen.
     */
    var clipboard = ""

    init {
        isVirtual = true
        setOtherScreen(this)
        webView.addOnResizeEventListener {
            setSize(it.newSize.width, it.newSize.height)
        }
    }

    /**
     * Highlights the given target.
     *
     * @param location Target to highlight.
     */
    override fun showTarget(location: Location) {
        val width = 50
        val height = 50
        val x = location.x - width / 2
        val y = location.y - height / 2
        WebViewHighlighter(webView, x, y, width, height).showForAndDispose(Settings.SlowMotionDelay)
    }

    /**
     * Gets the ID of a given point. Always 0 for a [SKryptonScreen]
     *
     * @param srcx x coordinate of point.
     * @param srcy y coordinate of point.
     */
    override fun getIdFromPoint(srcx: Int, srcy: Int): Int = 0

    /**
     * Gets the underlying robot instance used to control this screen.
     */
    override fun getRobot(): IRobot = robot

    /**
     * Same as `userCapture(null)` (Use default prompt string)
     */
    fun userCapture() = userCapture(null)
    /**
     * Shows a user prompt using a JavaScript alert. User will be required to click two separate
     * locations. First click will be the top left corner of the image. Second click will be the
     * bottom right. Selected area will be showing with a highlight to aid users.
     *
     * @param string Prompt string to show. Can be `null` to show default.
     * @return The captured image.
     */
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

    /**
     * Returns the ID of the screen, in this case returns the underlying web view native pointer value.
     */
    override fun getID(): Int = webView.handle.value.toInt()

    /**
     * Returns the geometry of the underlying web view.
     */
    override fun getBounds(): Rectangle = webView.geometry

    /**
     * Takes a screenshot of the whole screen.
     *
     * @return the captured image.
     */
    override fun capture(): ScreenImage = capture(rect)
    /**
     * Takes a screenshot of the screen.
     *
     * @param region Sub-region to capture.
     * @return the captured image.
     */
    override fun capture(region: Region): ScreenImage = capture(region.x, region.y, region.w, region.h)
    /**
     * Takes a screenshot of the screen.
     *
     * @param x x coordinate of the sub-region.
     * @param y y coordinate of the sub-region.
     * @param width width of the sub-region.
     * @param height height of the sub-region.
     * @return the captured image.
     */
    override fun capture(x: Int, y: Int, width: Int, height: Int): ScreenImage = capture(Rectangle(x, y, width, height))
    /**
     * Takes a screenshot of the screen.
     *
     * @param rect Sub-region to capture.
     * @return the captured image.
     */
    override fun capture(rect: Rectangle): ScreenImage = with(webView.takeScreenshot()) {
        ScreenImage(Rectangle(0, 0, this.width, this.height), this).getSub(rect)
    }

    /**
     * Returns the last saved screen image.
     */
    override fun getLastScreenImageFromScreen(): ScreenImage? = lastScreenImage

    /**
     * Gets the current position of the virtual mouse.
     */
    fun currentMousePosition() = Location(webView.cursorX, webView.cursorY)
}

/**
 * Creates a [SKryptonScreen] under this [SKryptonApp] instance
 *
 * @receiver [SKryptonApp]
 * @param url URL string to load initially.
 * @param showCursor Whether or not to show the virtual cursor on web view.
 * @param width Initial width of the web view.
 * @param height Initial height of the web view.
 * @param action Lambda function with created screen as its receiver.
 * @return Created [SKryptonScreen]
 */
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

/**
 * Creates a [SKryptonScreen] under this [SKryptonWebView] instance
 *
 * @receiver [SKryptonWebView]
 * @param action Lambda function with created screen as its receiver.
 * @return Created [SKryptonScreen]
 */
fun SKryptonWebView.screen(action: SKryptonScreen.() -> Unit = {}): SKryptonScreen {
    return SKryptonScreen(this).apply { action() }
}
