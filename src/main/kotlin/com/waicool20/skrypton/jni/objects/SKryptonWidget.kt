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

package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.enums.KeyEventType
import com.waicool20.skrypton.enums.MouseEventType
import com.waicool20.skrypton.jni.NativeInterface
import java.awt.Dimension
import java.awt.Rectangle

/**
 * A class representing a user interface element.
 */
abstract class SKryptonWidget : NativeInterface() {
    /**
     * The geometry of the widget. (x-y coordinates, width and height)
     */
    var geometry: Rectangle
        get() = getGeometry_N()
        set(value) = setGeometry_N(value.x, value.y, value.width, value.height)

    /**
     * Dimension of the widget. (Width and height only)
     */
    var size: Dimension
        get() = geometry.let { Dimension(it.width, it.height) }
        set(value) = resize_N(value.width, value.height)

    /**
     * Makes the widget visible.
     */
    fun show() = show_N()

    /**
     * Hides the widget.
     */
    fun hide() = hide_N()

    /**
     * Returns true if the widget is hidden.
     */
    fun isHidden() = isHidden_N()

    /**
     * Moves the widget to the given coordinates.
     *
     * @param x New x coordinate.
     * @param y New y coordinate.
     */
    fun move(x: Int, y: Int) = move_N(x, y)

    /**
     * Resizes the widget.
     *
     * @param width New width.
     * @param height New height.
     */
    fun resize(width: Int, height: Int) = resize_N(width, height)

    //<editor-fold desc="Mouse listener">

    private val onMouseEventListeners = mutableMapOf<MouseEventType, (event: SKryptonMouseEvent) -> Unit>()
    private fun onMouseEvent(type: Int, event: SKryptonMouseEvent) {
        onMouseEventListeners.filterKeys { it == MouseEventType.getForId(type) }.values.forEach { it(event) }
    }

    /**
     * Adds a listener for when a mouse event occurs on this widget.
     *
     * @param listener Listener used to receive this event. This lambda receives a variable
     * `event` which contains event details.
     */
    fun addOnMouseEventListener(type: MouseEventType, listener: (event: SKryptonMouseEvent) -> Unit) {
        onMouseEventListeners.put(type, listener)
    }

    /**
     * Removes given onMouseEvent listener.
     *
     * @param listener Listener used to receive this event.
     */
    fun removeOnMouseEventListener(type: MouseEventType, listener: (event: SKryptonMouseEvent) -> Unit) {
        onMouseEventListeners.remove(type, listener)
    }

    //</editor-fold>

    //<editor-fold desc="Wheel listener">

    private val onWheelEventListeners = mutableListOf<(event: SKryptonWheelEvent) -> Unit>()
    private fun onWheelEvent(event: SKryptonWheelEvent) {
        onWheelEventListeners.forEach { it(event) }
    }

    /**
     * Adds a listener for when a mouse wheel event occurs on this widget.
     *
     * @param listener Listener used to receive this event. This lambda receives a variable
     * `event` which contains event details.
     */
    fun addOnWheelEventListener(listener: (event: SKryptonWheelEvent) -> Unit) {
        onWheelEventListeners.add(listener)
    }

    /**
     * Removes given onWheelEvent listener.
     *
     * @param listener Listener used to receive this event.
     */
    fun removeOnWheelEventListener(listener: (event: SKryptonWheelEvent) -> Unit) {
        onWheelEventListeners.remove(listener)
    }

    //</editor-fold>

    //<editor-fold desc="Key listener">

    private val onKeyEventListeners = mutableMapOf<KeyEventType, (event: SKryptonKeyEvent) -> Unit>()
    private fun onKeyEvent(type: Int, event: SKryptonKeyEvent) {
        onKeyEventListeners.filterKeys { it == KeyEventType.getForId(type) }.values.forEach { it(event) }
    }

    /**
     * Adds a listener for when a key event occurs on this widget.
     *
     * @param listener Listener used to receive this event. This lambda receives a variable
     * `event` which contains event details.
     */
    fun addOnKeyEventListener(type: KeyEventType, listener: (event: SKryptonKeyEvent) -> Unit) {
        onKeyEventListeners.put(type, listener)
    }

    /**
     * Removes given onKeyEvent listener.
     *
     * @param listener Listener used to receive this event.
     */
    fun removeOnKeyEventListener(type: KeyEventType, listener: (event: SKryptonKeyEvent) -> Unit) {
        onKeyEventListeners.remove(type, listener)
    }

    //</editor-fold>

    //<editor-fold desc="Resize listener">

    private val onResizeEventListeners = mutableListOf<(event: SKryptonResizeEvent) -> Unit>()
    private fun onResizeEvent(event: SKryptonResizeEvent) {
        onResizeEventListeners.forEach { it(event) }
    }

    /**
     * Adds a listener for when a resize event occurs on this widget.
     *
     * @param listener Listener used to receive this event. This lambda receives a variable
     * `event` which contains event details.
     */
    fun addOnResizeEventListener(listener: (event: SKryptonResizeEvent) -> Unit) {
        onResizeEventListeners.add(listener)
    }

    /**
     * Removes given onResizeEvent listener.
     *
     * @param listener Listener used to receive this event.
     */
    fun removeOnResizeEventListener(listener: (event: SKryptonResizeEvent) -> Unit) {
        onResizeEventListeners.remove(listener)
    }

    //</editor-fold>

    //<editor-fold desc="Native functions">

    private external fun getGeometry_N(): Rectangle
    private external fun setGeometry_N(x: Int, y: Int, width: Int, height: Int)

    private external fun show_N()
    private external fun hide_N()
    private external fun isHidden_N(): Boolean
    private external fun move_N(x: Int, y: Int)
    private external fun resize_N(width: Int, height: Int)
    private external fun dispose_N()

    //</editor-fold>

    /**
     * Hides and closes this widget.
     */
    override fun close() = dispose_N()
}
