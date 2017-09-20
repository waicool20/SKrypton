package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.NativeInterface


abstract class SKryptonWidget : NativeInterface() {
    fun show() = show_N()
    fun hide() = hide_N()
    fun resize(width: Int, height: Int) = resize_N(width, height)

    private external fun show_N()
    private external fun hide_N()
    private external fun resize_N(width: Int, height: Int)
    private external fun dispose_N()

    override fun close() = dispose_N()
}
