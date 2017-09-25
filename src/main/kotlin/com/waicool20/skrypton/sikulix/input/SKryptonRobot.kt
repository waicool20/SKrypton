package com.waicool20.skrypton.sikulix.input

import com.waicool20.skrypton.enums.Key
import com.waicool20.skrypton.enums.KeyEventType
import com.waicool20.skrypton.enums.MouseButton
import com.waicool20.skrypton.enums.MouseEventType
import com.waicool20.skrypton.jni.objects.SKryptonKeyEvent
import com.waicool20.skrypton.jni.objects.SKryptonMouseEvent
import com.waicool20.skrypton.jni.objects.SKryptonWheelEvent
import com.waicool20.skrypton.sikulix.SKryptonScreen
import org.sikuli.basics.AnimatorOutQuarticEase
import org.sikuli.basics.AnimatorTimeBased
import org.sikuli.basics.Settings
import org.sikuli.script.*
import java.awt.Color
import java.awt.Point
import java.awt.Rectangle
import java.awt.event.KeyEvent
import java.util.*
import java.util.concurrent.TimeUnit


class SKryptonRobot(val screen: SKryptonScreen) : IRobot {
    private var autoDelay = 100
    private var heldButtons = 0
    private val heldKeys = mutableSetOf<Int>()
    private val webView = screen.webView
    private val rng = Random()

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
        webView.sendEvent(SKryptonWheelEvent(
                wheelAmt * 16 + rng.nextInt(2) - 1, // Just some variance
                Point(webView.cursorX, webView.cursorY)
        ))
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

    override fun keyUp() = heldKeys.forEach { keyUp(it) }
    override fun keyUp(code: Int) = generateKeyEvent(KeyEventType.KeyRelease, code)
    override fun keyUp(keys: String) = keys.toCharArray().forEach {
        webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyRelease, it))
    }

    override fun keyDown(code: Int) = generateKeyEvent(KeyEventType.KeyPress, code)
    override fun keyDown(keys: String) = keys.toCharArray().forEach {
        webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyPress, it))
    }

    override fun typeChar(character: Char, mode: IRobot.KeyMode) = when (mode) {
        IRobot.KeyMode.PRESS_ONLY -> webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyPress, character))
        IRobot.KeyMode.PRESS_RELEASE -> {
            webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyPress, character))
            webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyRelease, character))
        }
        IRobot.KeyMode.RELEASE_ONLY -> webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyRelease, character))
    }

    override fun typeKey(key: Int) {
        keyUp(key)
        keyDown(key)
    }

    override fun pressModifiers(modifiers: Int) {
        if (modifiers and KeyModifier.SHIFT != 0) keyDown(KeyEvent.VK_SHIFT)
        if (modifiers and KeyModifier.CTRL != 0) keyDown(KeyEvent.VK_CONTROL)
        if (modifiers and KeyModifier.ALT != 0) keyDown(KeyEvent.VK_ALT)
        if (modifiers and KeyModifier.META != 0 || modifiers and KeyModifier.WIN != 0) {
            if (Settings.isWindows()) {
                keyDown(KeyEvent.VK_WINDOWS)
            } else {
                keyDown(KeyEvent.VK_META)
            }
        }
    }

    override fun releaseModifiers(modifiers: Int) {
        if (modifiers and KeyModifier.SHIFT != 0) keyUp(KeyEvent.VK_SHIFT)
        if (modifiers and KeyModifier.CTRL != 0) keyUp(KeyEvent.VK_CONTROL)
        if (modifiers and KeyModifier.ALT != 0) keyUp(KeyEvent.VK_ALT)
        if (modifiers and KeyModifier.META != 0 || modifiers and KeyModifier.WIN != 0) {
            if (Settings.isWindows()) {
                keyUp(KeyEvent.VK_WINDOWS)
            } else {
                keyUp(KeyEvent.VK_META)
            }
        }
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
        val sButtons = MouseButton.fromSikuliButtons(buttons)
        webView.sendEvent(SKryptonMouseEvent(
                type = type,
                localPos = position,
                button = sButtons.firstOrNull() ?: MouseButton.NoButton,
                buttons = sButtons
        ))
    }

    private fun generateKeyEvent(type: KeyEventType, keyCode: Int) {
        if (keyCode in 32..127) {
            webView.sendEvent(SKryptonKeyEvent(
                    type = type,
                    char = keyCode.toChar()
            ))
        } else {
            webView.sendEvent(SKryptonKeyEvent(
                    type = type,
                    key = Key.fromSikuliKeyCode(keyCode)
            ))
        }
    }
}
