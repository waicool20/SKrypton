package com.waicool20.skrypton.sikulix

import com.waicool20.skrypton.jni.objects.SKryptonWebView
import com.waicool20.skrypton.sikulix.input.SKryptonRobot
import org.sikuli.script.*
import java.awt.Rectangle

class SKryptonScreen(val webView: SKryptonWebView) : Region(), IScreen {
    private val robot by lazy { SKryptonRobot(this) }

    init {
        isVirtual = true
        setOtherScreen(this)
    }

    override fun showTarget(location: Location?) {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun getIdFromPoint(srcx: Int, srcy: Int): Int = 0

    override fun getRobot(): IRobot = robot

    override fun userCapture(string: String?): ScreenImage {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun getID(): Int = webView.handle.value.toInt()

    override fun getBounds(): Rectangle = webView.geometry

    override fun capture(): ScreenImage = capture(rect)
    override fun capture(region: Region): ScreenImage = capture(region.x, region.y, region.w, region.h)
    override fun capture(x: Int, y: Int, width: Int, height: Int): ScreenImage = capture(Rectangle(x, y, w, h))
    override fun capture(rect: Rectangle): ScreenImage = with(webView.takeScreenshot()) {
        ScreenImage(Rectangle(0, 0, width, height), this).getSub(rect)
    }

    override fun getLastScreenImageFromScreen(): ScreenImage? = lastScreenImage
}
