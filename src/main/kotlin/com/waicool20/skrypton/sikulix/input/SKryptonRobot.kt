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

/**
 * A class representing a [org.sikuli.script.IRobot] that can be used to control a [SKryptonScreen],
 * not recommended for normal use as this is not thread safe, any actions generated will be
 * unpredictable behaviour. It is recommended to use [SKryptonKeyboard] and [SKryptonMouse] as
 * a thread safe wrapper to control this robot.
 *
 * @property screen [SKryptonScreen] that is bound to this robot.
 * @constructor Main constructor
 * @param screen [SKryptonScreen] to use this robot on.
 */
class SKryptonRobot(val screen: SKryptonScreen) : IRobot {
    private var autoDelay = 100
    private var heldButtons = 0
    private val heldKeys = mutableSetOf<Int>()
    private val webView = screen.webView
    private val rng = Random()

    //<editor-fold desc="Mouse stuff">

    /**
     * Releases mouse buttons.
     *
     * @param buttons Mouse buttons to release.
     * @return The currently held buttons.
     */
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

    /**
     * Presses mouse buttons.
     *
     * @param buttons Mouse buttons to press.
     */
    override fun mouseDown(buttons: Int) {
        heldButtons = if (heldButtons == 0) buttons else heldButtons or buttons
        generateMouseEvent(MouseEventType.MouseButtonPress, heldButtons)
    }

    /**
     * Moves the mouse to the coordinates instantly.
     *
     * @param x x coordinate to move the mouse to.
     * @param y y coordinate to move the mouse to.
     */
    override fun mouseMove(x: Int, y: Int) {
        generateMouseEvent(MouseEventType.MouseMove, 0, Point(x, y))
    }

    /**
     * Spins the mouse wheel.
     *
     * @param wheelAmt Amount of steps to spin the wheel, can be negative to change direction.
     */
    override fun mouseWheel(wheelAmt: Int) {
        webView.sendEvent(SKryptonWheelEvent(
                wheelAmt * 16 + rng.nextInt(2) - 1, // Just some variance
                Point(webView.cursorX, webView.cursorY)
        ))
    }

    /**
     * Moves the mouse to the destination in a smooth fashion.
     *
     * @param dest Location to move the mouse to.
     */
    override fun smoothMove(dest: Location) =
            smoothMove(Location(webView.cursorX, webView.cursorY), dest, (Settings.MoveMouseDelay * 1000).toLong())

    /**
     * Moves the mouse from a source to the destination in a smooth fashion.
     *
     * @param src Location to move the mouse from
     * @param dest Location to move the mouse to.
     * @param ms Length of time to complete this action within in milliseconds.
     */
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

    /**
     * Resets the mouse state. Mouse is moved to (0, 0) and all buttons are released.
     */
    override fun mouseReset() {
        mouseMove(0, 0)
        mouseUp(Mouse.LEFT and Mouse.MIDDLE and Mouse.RIGHT)
    }

    //</editor-fold>

    //<editor-fold desc="Keyboard stuff">

    /**
     * Releases all keys.
     */
    override fun keyUp() = heldKeys.forEach { keyUp(it) }
    /**
     * Releases key with the given code.
     *
     * @param code Key to release.
     */
    override fun keyUp(code: Int) = generateKeyEvent(KeyEventType.KeyRelease, code)
    /**
     * Releases keys specified in a string
     *
     * @param keys Keys to release.
     */
    override fun keyUp(keys: String) = keys.toCharArray().forEach {
        webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyRelease, it))
    }

    /**
     * Presses key with the given code.
     *
     * @param code Key to press.
     */
    override fun keyDown(code: Int) = generateKeyEvent(KeyEventType.KeyPress, code)
    /**
     * Presses keys specified in a string.
     *
     * @param keys Keys to press.
     */
    override fun keyDown(keys: String) = keys.toCharArray().forEach {
        webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyPress, it))
    }

    /**
     * Types a given character.
     *
     * @param character Character to type.
     * @param mode Mode to type the character with.
     */
    override fun typeChar(character: Char, mode: IRobot.KeyMode) = when (mode) {
        IRobot.KeyMode.PRESS_ONLY -> webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyPress, character))
        IRobot.KeyMode.PRESS_RELEASE -> {
            webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyPress, character))
            webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyRelease, character))
        }
        IRobot.KeyMode.RELEASE_ONLY -> webView.sendEvent(SKryptonKeyEvent(KeyEventType.KeyRelease, character))
    }

    /**
     * Types a given key.
     *
     * @param key Key to type.
     */
    override fun typeKey(key: Int) {
        keyUp(key)
        keyDown(key)
    }

    /**
     * Presses key modifiers.
     *
     * @param modifiers The key modifiers to press.
     */
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

    /**
     * Releases key modifiers.
     *
     * @param modifiers The key modifiers to release.
     */
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

    /**
     * Sets the auto delay for this robot.
     */
    override fun setAutoDelay(ms: Int) {
        autoDelay = ms
    }

    /**
     * Returns whether or not the robot is controlling a remote screen. Always true.
     */
    override fun isRemote() = true

    /**
     * Sleeps the current thread for a duration
     *
     * @param ms Time to sleep in milliseconds.
     */
    override fun delay(ms: Int) = TimeUnit.MILLISECONDS.sleep(ms.toLong())

    //</editor-fold>

    //<editor-fold desc="Screen Stuff">

    /**
     * Takes a screenshot of the screen bound to this robot
     *
     * @param screenRect Sub-region to capture.
     * @return the captured image.
     */
    override fun captureScreen(screenRect: Rectangle): ScreenImage = screen.capture(screenRect)

    /**
     * Gets the bound screen instance.
     */
    override fun getScreen(): IScreen = screen

    /**
     * Gets the color of the pixel at coordinates.
     *
     * @param x x coordinate of the pixel.
     * @param y y coordinate of the pixel.
     */
    override fun getColorAt(x: Int, y: Int): Color = Color((screen.lastScreenImage ?: screen.capture()).image.getRGB(x, y))

    //</editor-fold>

    //<editor-fold desc="Stuff that does Nothing">

    /**
     * Does nothing, ignore.
     */
    override fun cleanup() = Unit
    /**
     * Does nothing, ignore.
     */
    override fun clickStarts() = Unit
    /**
     * Does nothing, ignore.
     */
    override fun clickEnds() = Unit
    /**
     * Does nothing, ignore.
     */
    override fun typeStarts() = Unit
    /**
     * Does nothing, ignore.
     */
    override fun typeEnds() = Unit
    /**
     * Does nothing, ignore.
     */
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
