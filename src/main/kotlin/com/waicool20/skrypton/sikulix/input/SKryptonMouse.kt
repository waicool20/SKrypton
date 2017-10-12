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

/**
 * Class representing a virtual mouse for a [com.waicool20.skrypton.sikulix.SKryptonScreen]
 * This class is also in charge of coordinating mouse actions between threads, unlike [SKryptonRobot]
 * all functions are synchronized and thus only one thread may have access to keyboard actions.
 *
 * @property robot [SKryptonRobot] used for generating actions.
 * @constructor Main constructor
 * @param robot [SKryptonRobot] to use for generating actions.
 */
class SKryptonMouse(val robot: SKryptonRobot) {
    /**
     * Sends a click at a given location
     *
     * @param location Location to click.
     * @param buttons The mouse buttons to click.
     * @param modifiers Key modifiers to press during clicking.
     */
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

    /**
     * Sends a double click at a given location
     *
     * @param location Location to click.
     * @param buttons The mouse buttons to click.
     * @param modifiers Key modifiers to press during clicking.
     */
    @Synchronized
    fun doubleClick(location: Location, buttons: Int, modifiers: Int) = synchronized(this) {
        repeat(2) {
            click(location, buttons, modifiers)
        }
    }

    /**
     * Moves the mouse cursor to a given location.
     *
     * @param location The location to move the mouse cursor to.
     */
    @Synchronized
    fun moveTo(location: Location) = synchronized(this) { robot.smoothMove(location) }

    /* Low level actions */

    /**
     * Presses mouse buttons.
     *
     * @param buttons The mouse buttons to press.
     */
    @Synchronized
    fun mouseDown(buttons: Int) = synchronized(this) { robot.mouseDown(buttons) }

    /**
     * Releases mouse buttons.
     *
     * @param buttons The mouse buttons to release.
     */
    @Synchronized
    fun mouseUp(buttons: Int = 0): Int = synchronized(this) { robot.mouseUp(buttons) }

    /**
     * Spins the mouse wheel.
     *
     * @param location Location to spin the mouse wheel at,
     * may be null to just spin the wheel directly.
     * @param direction Direction to spin the mouse wheel in.
     * @param steps Number of steps the wheel is spinned.
     * @param stepDelay The delay in milliseconds between each wheel step.
     */
    @Synchronized
    fun spinWheel(location: Location?, direction: Int, steps: Int, stepDelay: Int) = synchronized(this) {
        location?.let { moveTo(it) }
        repeat(steps) {
            robot.mouseWheel(if (direction < 0) -1 else 1)
            robot.delay(stepDelay)
        }
    }

    /**
     * Initiates a mouse drag action. (Moves mouse to a location and presses without releasing)
     *
     * @param location Location to start the mouse drag action
     * @param resetDelays Whether or not to reset delays after pressing.
     */
    @Synchronized
    fun drag(location: Location, resetDelays: Boolean = true) = synchronized(this) {
        moveTo(location)
        robot.delay((Settings.DelayBeforeMouseDown * 1000).toInt())
        mouseDown(Mouse.LEFT)
        robot.delay((if (Settings.DelayBeforeDrag < 0) Settings.DelayAfterDrag else Settings.DelayBeforeDrag).toInt() * 1000)
        if (resetDelays) resetDragDelays()
    }

    /**
     * Ends a mouse drop action. (Moves mouse to a location and releases the button)
     *
     * @param location Location to release the mouse button.
     * @param resetDelays Whether or not to reset delays after releasing.
     */
    @Synchronized
    fun dropAt(location: Location, resetDelays: Boolean = true) = synchronized(this) {
        moveTo(location)
        robot.delay((Settings.DelayBeforeDrop * 1000).toInt())
        mouseUp(Mouse.LEFT)
        if (resetDelays) resetDragDelays()
    }

    /**
     * Same as calling [drag] with [loc1] then [dropAt] with [loc2] but does this all at once while
     * keeping the lock to this object.
     *
     * @param loc1 Location to begin drag.
     * @param loc2 Location to drop at.
     */
    @Synchronized
    fun dragDrop(loc1: Location, loc2: Location) = synchronized(this) {
        drag(loc1, false)
        dropAt(loc2)
    }

    /**
     * Resets drag and drop delays to default.
     */
    @Synchronized
    private fun resetDragDelays() {
        Settings.DelayBeforeMouseDown = Settings.DelayValue
        Settings.DelayAfterDrag = Settings.DelayValue
        Settings.DelayBeforeDrag = -Settings.DelayValue
        Settings.DelayBeforeDrop = Settings.DelayValue
    }

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
