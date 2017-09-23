package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import java.awt.Color

class WebViewHighlighter private constructor(override val handle: CPointer): SKryptonWidget() {

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
    ): this(CPointer(initialize_N(
            view, x, y, width, height,
            fillColor, color.red, color.green, color.blue
    )))
}
