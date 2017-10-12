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

import com.waicool20.skrypton.enums.KeyboardModifiers
import com.waicool20.skrypton.enums.MouseButton
import com.waicool20.skrypton.enums.MouseEventSource
import com.waicool20.skrypton.enums.MouseEventType
import java.awt.Point

/**
 * An event which indicates that a mouse action occurred.
 */
class SKryptonMouseEvent private constructor(pointer: Long) : SKryptonEvent(pointer) {
    private companion object {
        private external fun initialize_N(
                type: Int,
                localPosX: Int,
                localPosY: Int,
                button: Long,
                buttons: Long,
                modifiers: Long
        ): Long
    }

    //<editor-fold desc="Constructor">
    /**
     * Main constructor.
     *
     * @param type Type of mouse event.
     * @param localPos Point object of the mouse event relative to the top-left corner of the
     * [SKryptonWebView].
     * @param button Button that triggered the event.
     * @param buttons Buttons that were involved in the event.
     * @param modifiers Modifiers that were involved in the event.
     */
    constructor(
            type: MouseEventType,
            localPos: Point,
            button: MouseButton = MouseButton.NoButton,
            buttons: Set<MouseButton> = setOf(button),
            modifiers: KeyboardModifiers = KeyboardModifiers.NoModifier
    ) : this(initialize_N(
            type.id,
            localPos.x, localPos.y,
            button.value,
            if (button == MouseButton.NoButton) {
                MouseButton.NoButton.value
            } else {
                buttons.map { it.value }.reduce { acc, l -> acc or l }
            },
            modifiers.value
    ))
    //</editor-fold>

    /**
     * x coordinates relative to the top-left corner of the [SKryptonWebView].
     */
    val x by lazy { localPos.x }
    /**
     * y coordinates relative to the top-left corner of the [SKryptonWebView].
     */
    val y by lazy { localPos.y }

    /**
     * x coordinates relative to the top-left corner of the screen.
     */
    val globalX by lazy { globalPos.x }
    /**
     * y coordinates relative to the top-left corner of the screen.
     */
    val globalY by lazy { globalPos.y }

    /**
     * Button that triggered this event.
     */
    val button by lazy { MouseButton.getForValue(getButton_N()) }
    /**
     * Buttons involved in this event.
     */
    val buttons by lazy {
        val buttons = getButtons_N()
        MouseButton.values().filter { it.value and buttons == it.value }.toSet()
    }

    /**
     * Point object relative to the top-left corner of the screen.
     */
    val globalPos by lazy { getGlobalPos_N() }
    /**
     * Same as [globalPos].
     */
    val screenPos by lazy { getScreenPos_N() }
    /**
     * Point object relative to the top-left corner of the [SKryptonWebView].
     */
    val localPos by lazy { getLocalPos_N() }
    /**
     * Event source.
     */
    val source by lazy { MouseEventSource.values()[getSource_N()] }

    private external fun getButton_N(): Long
    private external fun getButtons_N(): Long
    private external fun getGlobalPos_N(): Point
    private external fun getScreenPos_N(): Point
    private external fun getLocalPos_N(): Point
    private external fun getSource_N(): Int
}
