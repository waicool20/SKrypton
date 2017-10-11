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

import com.waicool20.skrypton.enums.*
import java.awt.Point

class SKryptonWheelEvent private constructor(pointer: Long) : SKryptonEvent(pointer) {
    private companion object {
        private external fun initialize_N(
                localPosX: Int, localPosY: Int,
                delta: Int,
                buttons: Long,
                modifiers: Long,
                orientation: Int
        ): Long
    }

    constructor(
            delta: Int,
            localPos: Point,
            buttons: List<MouseButton> = listOf(MouseButton.NoButton),
            modifiers: KeyboardModifiers = KeyboardModifiers.NoModifier,
            orientation: Orientation = Orientation.Vertical
    ) : this(initialize_N(
            localPos.x, localPos.y,
            delta,
            buttons.map { it.value }.reduce { acc, l -> acc or l },
            modifiers.value,
            orientation.ordinal + 1
    ))

    val x by lazy { localPos.x }
    val y by lazy { localPos.y }

    val globalX by lazy { globalPos.x }
    val globalY by lazy { globalPos.y }

    val buttons by lazy {
        val buttons = getButtons_N()
        MouseButton.values().filter { it.value and buttons == it.value }.toSet()
    }

    val delta by lazy { getDelta_N() }
    val isInverted by lazy { isInverted_N() }
    val phase by lazy { ScrollPhase.values()[getPhase_N()] }
    val globalPos by lazy { getGlobalPos_N() }
    val localPos by lazy { getLocalPos_N() }
    val source by lazy { MouseEventSource.values()[getSource_N()] }

    private external fun isInverted_N(): Boolean
    private external fun getPhase_N(): Int
    private external fun getDelta_N(): Int
    private external fun getLocalPos_N(): Point
    private external fun getGlobalPos_N(): Point
    private external fun getButtons_N(): Long
    private external fun getSource_N(): Int
}
