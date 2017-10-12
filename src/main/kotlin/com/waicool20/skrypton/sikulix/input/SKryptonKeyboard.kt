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

import org.sikuli.basics.Settings
import org.sikuli.script.Key
import org.sikuli.script.KeyModifier
import org.sikuli.script.Location

/**
 * Class representing a virtual keyboard for a [com.waicool20.skrypton.sikulix.SKryptonScreen]
 * This class is also in charge of coordinating keyboard actions between threads, unlike [SKryptonRobot]
 * all functions are synchronized and thus only one thread may have access to keyboard actions.
 *
 * @property robot [SKryptonRobot] used for generating actions.
 * @constructor Main constructor
 * @param robot [SKryptonRobot] to use for generating actions.
 */
class SKryptonKeyboard(val robot: SKryptonRobot) {
    companion object {
        /**
         * Parses the given modifiers string and returns corresponding [KeyModifier].
         *
         * @param modifiers String of modifiers.
         * @return Corresponding [KeyModifier].
         */
        fun parseModifiers(modifiers: String): Int {
            var mods = 0
            modifiers.toCharArray().forEach {
                mods = mods.or(when (it) {
                    Key.C_CTRL -> KeyModifier.CTRL
                    Key.C_ALT -> KeyModifier.ALT
                    Key.C_SHIFT -> KeyModifier.SHIFT
                    Key.C_META -> KeyModifier.META
                    Key.C_ALTGR -> KeyModifier.ALTGR
                    Key.C_WIN -> KeyModifier.WIN
                    else -> 0
                })
            }
            return mods
        }
    }

    /**
     * Types text at a given location
     *
     * @param location Location to type to, can be null to just type directly into the screen.
     * @param text The text to type.
     * @param modifiers Key modifiers to press during typing.
     */
    @Synchronized
    fun type(location: Location?, text: String, modifiers: Int) = synchronized(this) {
        if (location != null) robot.screen.click(location)
        val pause = if (Settings.TypeDelay > 1) 1 else (Settings.TypeDelay * 1000).toInt()
        robot.pressModifiers(modifiers)
        text.toCharArray().map(Char::toInt).forEach {
            robot.typeKey(it)
            robot.delay(if (pause < 80) 80 else pause)
        }
        Settings.TypeDelay = 0.0
    }

    /**
     * Releases all keys
     */
    @Synchronized
    fun keyUp() = synchronized(this) { robot.keyUp() }

    /**
     * Releases a specific key.
     *
     * @param keycode The key to release.
     */
    @Synchronized
    fun keyUp(keycode: Int) = synchronized(this) { robot.keyUp(keycode) }

    /**
     * Releases the keys specified by the string.
     *
     * @param keys Keys to be released.
     */
    @Synchronized
    fun keyUp(keys: String) = synchronized(this) { robot.keyUp(keys) }

    /**
     * Presses a specific key.
     *
     * @param keycode The key to press.
     */
    @Synchronized
    fun keyDown(keycode: Int) = synchronized(this) { robot.keyDown(keycode) }

    /**
     * Presses the keys specified by the string.
     *
     * @param keys Keys to be pressed.
     */
    @Synchronized
    fun keyDown(keys: String) = synchronized(this) { robot.keyDown(keys) }

    /**
     * Executes an action atomically while keeping the synchronized lock to this object.
     * Useful if you want to do multiple actions in one go without the possibility of a thread
     * stealing ownership.
     *
     * @param action Action to execute while keeping the lock this object.
     * @return Result of [action]
     */
    @Synchronized inline fun <T> atomicAction(action: () -> T): T = synchronized(this) { action() }
}
