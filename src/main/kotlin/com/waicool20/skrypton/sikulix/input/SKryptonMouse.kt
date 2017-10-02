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
import org.sikuli.script.Location
import org.sikuli.script.Mouse


class SKryptonMouse(val robot: SKryptonRobot) {
    @Synchronized
    fun click(location: Location, buttons: Int, modifiers: Int) = synchronized(this) {
        moveTo(location)
        val pause = if (Settings.ClickDelay > 1) 1 else (Settings.ClickDelay * 1000).toInt()
        robot.pressModifiers(modifiers)
        robot.mouseDown(buttons)
        robot.delay(pause)
        robot.mouseUp(buttons)
        robot.releaseModifiers(modifiers)
        Settings.ClickDelay = 0.0
    }

    @Synchronized
    fun doubleClick(location: Location, buttons: Int, modifiers: Int) = synchronized(this) {
        repeat(2) {
            click(location, buttons, modifiers)
        }
    }

    @Synchronized
    fun moveTo(location: Location) = synchronized(this) { robot.smoothMove(location) }

    /* Low level actions */

    @Synchronized
    fun mouseDown(buttons: Int) = synchronized(this) { robot.mouseDown(buttons) }

    @Synchronized
    fun mouseUp(buttons: Int = 0): Int = synchronized(this) { robot.mouseUp(buttons) }

    @Synchronized
    fun spinWheel(location: Location, direction: Int, steps: Int, stepDelay: Int) = synchronized(this) {
        moveTo(location)
        repeat(steps) {
            robot.mouseWheel(if (direction < 0) -1 else 1)
            robot.delay(stepDelay)
        }
    }

    @Synchronized
    fun drag(location: Location, resetDelays: Boolean = true) = synchronized(this) {
        moveTo(location)
        robot.delay((Settings.DelayBeforeMouseDown * 1000).toInt())
        mouseDown(Mouse.LEFT)
        robot.delay((if (Settings.DelayBeforeDrag < 0) Settings.DelayAfterDrag else Settings.DelayBeforeDrag).toInt() * 1000)
        if (resetDelays) resetDragDelays()
        1
    }

    @Synchronized
    fun dropAt(location: Location, resetDelays: Boolean = true) = synchronized(this) {
        moveTo(location)
        robot.delay((Settings.DelayBeforeDrop * 1000).toInt())
        mouseUp(Mouse.LEFT)
        if (resetDelays) resetDragDelays()
        1
    }

    @Synchronized
    fun dragDrop(loc1: Location, loc2: Location) = synchronized(this) {
        drag(loc1, false)
        dropAt(loc2)
        1
    }

    @Synchronized
    private fun resetDragDelays() {
        Settings.DelayBeforeMouseDown = Settings.DelayValue
        Settings.DelayAfterDrag = Settings.DelayValue
        Settings.DelayBeforeDrag = -Settings.DelayValue
        Settings.DelayBeforeDrop = Settings.DelayValue
    }

    @Synchronized inline fun <T> atomicAction(action: () -> T): T = synchronized(this) { action() }
}
