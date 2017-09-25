package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import java.awt.Color
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

class WebViewHighlighter private constructor(override val handle: CPointer) : SKryptonWidget() {
    private val timer = Timer()

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

    fun showFor(seconds: Float, callback: () -> Unit = {}) = showFor(seconds.toDouble(), callback)
    fun showFor(seconds: Double, callback: () -> Unit = {}) = showFor((seconds * 1000).toLong(), TimeUnit.MILLISECONDS, callback)
    fun showFor(duration: Long, unit: TimeUnit = TimeUnit.SECONDS, callback: () -> Unit = {}) {
        show()
        timer.schedule(unit.toMillis(duration)) {
            hide()
            callback()
        }
    }

    fun showForAndDispose(seconds: Float, callback: () -> Unit = {}) = showForAndDispose(seconds.toDouble(), callback)
    fun showForAndDispose(seconds: Double, callback: () -> Unit = {}) = showForAndDispose((seconds * 1000).toLong(), TimeUnit.MILLISECONDS, callback)
    fun showForAndDispose(duration: Long, unit: TimeUnit = TimeUnit.SECONDS, callback: () -> Unit = {}) {
        show()
        timer.schedule(unit.toMillis(duration)) {
            close()
            callback()
        }
    }

    fun toggle() {
        if (isHidden()) {
            show()
        } else {
            hide()
        }
    }

    private external fun getColor_N(): Color
    private external fun setColor_N(red: Int, green: Int, blue: Int)
    private external fun isFillColor_N(): Boolean
    private external fun setFillColor_N(fillColor: Boolean)
}
