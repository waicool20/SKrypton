package com.waicool20.skrypton.sikulix.input

import com.waicool20.skrypton.enums.MouseButton
import com.waicool20.skrypton.enums.MouseEventType
import com.waicool20.skrypton.jni.objects.SKryptonMouseEvent
import com.waicool20.skrypton.sikulix.SKryptonScreen
import org.sikuli.basics.AnimatorOutQuarticEase
import org.sikuli.basics.AnimatorTimeBased
import org.sikuli.basics.Settings
import org.sikuli.script.*
import java.awt.Color
import java.awt.Point
import java.awt.Rectangle
import java.util.concurrent.TimeUnit


class SKryptonRobot(val screen: SKryptonScreen) : IRobot {
    private var autoDelay = 100
    private var heldButtons = 0
    private val webView = screen.webView

    //<editor-fold desc="Mouse stuff">

    override fun mouseUp(buttons: Int): Int {
        heldButtons = if (buttons == 0) {
            generateMouseEvent(MouseEventType.MouseButtonRelease, heldButtons)
            0
        } else {
            generateMouseEvent(MouseEventType.MouseButtonRelease, buttons)
            heldButtons and buttons.inv()
        }
        return heldButtons
    }

    override fun mouseDown(buttons: Int) {
        heldButtons = if (heldButtons == 0) buttons else heldButtons or buttons
        generateMouseEvent(MouseEventType.MouseButtonPress, heldButtons)
    }

    override fun mouseMove(x: Int, y: Int) {
        generateMouseEvent(MouseEventType.MouseMove, 0, Point(x, y))
    }

    override fun mouseWheel(wheelAmt: Int) {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun smoothMove(dest: Location) =
            smoothMove(Location(webView.cursorX, webView.cursorY), dest, (Settings.MoveMouseDelay * 1000).toLong())

    override fun smoothMove(src: Location, dest: Location, ms: Long) {
        if (ms < 1) {
            mouseMove(dest.x, dest.y)
            return
        }
        val aniX = AnimatorTimeBased(AnimatorOutQuarticEase(src.x.toFloat(), dest.x.toFloat(), ms))
        val aniY = AnimatorTimeBased(AnimatorOutQuarticEase(src.y.toFloat(), dest.y.toFloat(), ms))
        while (aniX.running()) {
            val x = aniX.step()
            val y = aniY.step()
            mouseMove(x.toInt(), y.toInt())
            delay(10)
        }
    }

    override fun mouseReset() {
        mouseMove(0, 0)
        mouseUp(Mouse.LEFT and Mouse.MIDDLE and Mouse.RIGHT)
    }

    //</editor-fold>

    //<editor-fold desc="Keyboard stuff">

    override fun keyUp(code: Int) {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun keyUp() {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun keyDown(keys: String?) {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun keyDown(code: Int) {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun typeChar(character: Char, mode: IRobot.KeyMode?) {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun typeKey(key: Int) {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun keyUp(keys: String?) {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun pressModifiers(modifiers: Int) {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    override fun releaseModifiers(modifiers: Int) {
        throw UnsupportedOperationException("Not Implemented") // TODO Implement this function
    }

    //</editor-fold>

    //<editor-fold desc="Misc">

    override fun setAutoDelay(ms: Int) {
        autoDelay = ms
    }

    override fun isRemote() = true

    override fun delay(ms: Int) = TimeUnit.MILLISECONDS.sleep(ms.toLong())

    //</editor-fold>

    //<editor-fold desc="Screen Stuff">

    override fun captureScreen(screenRect: Rectangle): ScreenImage = screen.capture(screenRect)
    override fun getScreen(): IScreen = screen
    override fun getColorAt(x: Int, y: Int): Color = Color((screen.lastScreenImage ?: screen.capture()).image.getRGB(x, y))

    //</editor-fold>

    //<editor-fold desc="Stuff that does Nothing">

    override fun cleanup() = Unit
    override fun clickStarts() = Unit
    override fun clickEnds() = Unit
    override fun typeStarts() = Unit
    override fun typeEnds() = Unit
    override fun waitForIdle() = Unit

    //</editor-fold>

    private fun generateMouseEvent(type: MouseEventType, buttons: Int, position: Point = Point(webView.cursorX, webView.cursorY)) {
        webView.sendEvent(SKryptonMouseEvent(
                type = type,
                localPos = position,
                buttons = MouseButton.fromSikuliButtons(buttons)
        ))
    }
}
