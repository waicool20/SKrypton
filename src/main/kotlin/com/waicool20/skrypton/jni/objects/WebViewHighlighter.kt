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

import com.waicool20.skrypton.jni.CPointer
import java.awt.Color
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

/**
 * A class that provides a colored rectangle overlay to highlight certain areas of a web view.
 */
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

    /**
     * Main constructor
     *
     * @param view Web view to use this highlighter with.
     * @param x x coordinates where top left corner of the highlighter should begin.
     * @param y y coordinates where top left corner of the highlighter should begin.
     * @param width Width of the highlighter rectangle.
     * @param height Height of the highlighter rectangle.
     * @param fillColor Whether or not the highlight should filled or just a border.
     * @param color Color of the highlight.
     */
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

    /**
     * Color of the highlight.
     */
    var color: Color
        get() = getColor_N()
        set(value) = setColor_N(value.red, value.green, value.blue)

    /**
     * Whether or not the highlight should filled or just a border.
     */
    var fillColor: Boolean
        get() = isFillColor_N()
        set(value) = setFillColor_N(value)

    /**
     * Shows the highlight for a given duration.
     *
     * @param seconds Duration to show this highlight for in seconds.
     * @param callback Called when the highlight is done showing.
     */
    fun showFor(seconds: Float, callback: () -> Unit = {}) = showFor(seconds.toDouble(), callback)
    /**
     * Shows the highlight for a given duration.
     *
     * @param seconds Duration to show this highlight for in seconds.
     * @param callback Called when the highlight is done showing.
     */
    fun showFor(seconds: Double, callback: () -> Unit = {}) = showFor((seconds * 1000).toLong(), TimeUnit.MILLISECONDS, callback)
    /**
     * Shows the highlight for a given duration.
     *
     * @param duration Duration to show this highlight for.
     * @param unit Unit of the duration.
     * @param callback Called when the highlight is done showing.
     */
    fun showFor(duration: Long, unit: TimeUnit = TimeUnit.SECONDS, callback: () -> Unit = {}) {
        show()
        timer.schedule(unit.toMillis(duration)) {
            hide()
            callback()
        }
    }

    /**
     * Same as [showFor] but disposes the highlighter before calling the callback.
     *
     * @param seconds Duration to show this highlight for in seconds.
     * @param callback Called when the highlight is done showing.
     */
    fun showForAndDispose(seconds: Float, callback: () -> Unit = {}) = showForAndDispose(seconds.toDouble(), callback)
    /**
     * Same as [showFor] but disposes the highlighter before calling the callback.
     *
     * @param seconds Duration to show this highlight for in seconds.
     * @param callback Called when the highlight is done showing.
     */
    fun showForAndDispose(seconds: Double, callback: () -> Unit = {}) = showForAndDispose((seconds * 1000).toLong(), TimeUnit.MILLISECONDS, callback)
    /**
     * Same as [showFor] but disposes the highlighter before calling the callback.
     *
     * @param duration Duration to show this highlight for.
     * @param unit Unit of the duration.
     * @param callback Called when the highlight is done showing.
     */
    fun showForAndDispose(duration: Long, unit: TimeUnit = TimeUnit.SECONDS, callback: () -> Unit = {}) {
        show()
        timer.schedule(unit.toMillis(duration)) {
            close()
            callback()
        }
    }

    /**
     * Toggles the visibility of the highlighter.
     */
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
