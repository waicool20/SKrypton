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

    val x by lazy { localPos.x }
    val y by lazy { localPos.y }

    val globalX by lazy { globalPos.x }
    val globalY by lazy { globalPos.y }

    val button by lazy { MouseButton.getForValue(getButton_N()) }
    val buttons by lazy {
        val buttons = getButtons_N()
        MouseButton.values().filter { it.value and buttons == it.value }.toSet()
    }
    val globalPos by lazy { getGlobalPos_N() }
    val screenPos by lazy { getScreenPos_N() }
    val localPos by lazy { getLocalPos_N() }
    val source by lazy { MouseEventSource.values()[getSource_N()] }

    private external fun getButton_N(): Long
    private external fun getButtons_N(): Long
    private external fun getGlobalPos_N(): Point
    private external fun getScreenPos_N(): Point
    private external fun getLocalPos_N(): Point
    private external fun getSource_N(): Int
}
