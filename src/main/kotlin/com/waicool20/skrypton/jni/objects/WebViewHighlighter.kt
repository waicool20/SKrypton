package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import java.awt.Color

class WebViewHighlighter private constructor(override val handle: CPointer) : SKryptonWidget() {

    private companion object {
        private external fun initialize_N(
                view: SKryptonWebView,
                x: Int, y: Int,
                width: Int, height: Int,
                fillColor: Boolean,
                red: Int, green: Int, blue: Int
        ): Long
    }

    constructor(
            view: SKryptonWebView,
            x: Int, y: Int,
            width: Int, height: Int,
            fillColor: Boolean = false,
            color: Color = Color.GREEN
    ) : this(CPointer(initialize_N(
            view, x, y, width, height,
            fillColor, color.red, color.green, color.blue
    )))

    var color: Color
        get() = getColor_N()
        set(value) = setColor_N(value.red, value.green, value.blue)

    var fillColor: Boolean
        get() = isFillColor_N()
        set(value) = setFillColor_N(value)

    private external fun getColor_N(): Color
    private external fun setColor_N(red: Int, green: Int, blue: Int)
    private external fun isFillColor_N(): Boolean
    private external fun setFillColor_N(fillColor: Boolean)
}
