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

abstract class SKryptonWidget : NativeInterface() {
    var geometry: Rectangle
        get() = getGeometry_N()
        set(value) = setGeometry_N(value.x, value.y, value.width, value.height)

    var size: Dimension
        get() = geometry.let { Dimension(it.width, it.height) }
        set(value) = resize_N(value.width, value.height)

    fun show() = show_N()
    fun hide() = hide_N()
    fun isHidden() = isHidden_N()
    fun move(x: Int, y: Int) = move_N(x, y)
    fun resize(width: Int, height: Int) = resize_N(width, height)

    //<editor-fold desc="Mouse listener">

    private val onMouseEventListeners = mutableMapOf<MouseEventType, (event: SKryptonMouseEvent) -> Unit>()
    private fun onMouseEvent(type: Int, event: SKryptonMouseEvent) {
        onMouseEventListeners.filterKeys { it == MouseEventType.getForId(type) }.values.forEach { it(event) }
    }

    fun addOnMouseEventListener(type: MouseEventType, listener: (event: SKryptonMouseEvent) -> Unit) {
        onMouseEventListeners.put(type, listener)
    }

    fun removeOnMouseEventListener(type: MouseEventType, listener: (event: SKryptonMouseEvent) -> Unit) {
        onMouseEventListeners.remove(type, listener)
    }

    //</editor-fold>

    //<editor-fold desc="Wheel listener">

    private val onWheelEventListeners = mutableListOf<(event: SKryptonWheelEvent) -> Unit>()
    private fun onWheelEvent(event: SKryptonWheelEvent) {
        onWheelEventListeners.forEach { it(event) }
    }

    fun addOnWheelEventListener(listener: (event: SKryptonWheelEvent) -> Unit) {
        onWheelEventListeners.add(listener)
    }

    fun removeOnWheelEventListener(listener: (event: SKryptonWheelEvent) -> Unit) {
        onWheelEventListeners.remove(listener)
    }

    //</editor-fold>

    //<editor-fold desc="Key listener">

    private val onKeyEventListeners = mutableMapOf<KeyEventType, (event: SKryptonKeyEvent) -> Unit>()
    private fun onKeyEvent(type: Int, event: SKryptonKeyEvent) {
        onKeyEventListeners.filterKeys { it == KeyEventType.getForId(type) }.values.forEach { it(event) }
    }

    fun addOnKeyEventListener(type: KeyEventType, listener: (event: SKryptonKeyEvent) -> Unit) {
        onKeyEventListeners.put(type, listener)
    }

    fun removeOnKeyEventListener(type: KeyEventType, listener: (event: SKryptonKeyEvent) -> Unit) {
        onKeyEventListeners.remove(type, listener)
    }

    //</editor-fold>

    //<editor-fold desc="Resize listener">

    private val onResizeEventListeners = mutableListOf<(event: SKryptonResizeEvent) -> Unit>()
    private fun onResizeEvent(event: SKryptonResizeEvent) {
        onResizeEventListeners.forEach { it(event) }
    }

    fun addOnResizeEventListener(listener: (event: SKryptonResizeEvent) -> Unit) {
        onResizeEventListeners.add(listener)
    }

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

    override fun close() = dispose_N()
}
