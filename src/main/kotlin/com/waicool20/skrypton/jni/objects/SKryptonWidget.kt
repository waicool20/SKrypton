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
