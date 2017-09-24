package com.waicool20.skrypton.jni.objects

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
    fun move(x: Int, y: Int) = move_N(x, y)
    fun resize(width: Int, height: Int) = resize_N(width, height)

    private external fun getGeometry_N(): Rectangle
    private external fun setGeometry_N(x: Int, y: Int, width: Int, height: Int)

    private external fun show_N()
    private external fun hide_N()
    private external fun move_N(x: Int, y: Int)
    private external fun resize_N(width: Int, height: Int)
    private external fun dispose_N()

    override fun close() = dispose_N()
}
