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

package com.waicool20.skrypton.enums

import com.waicool20.skrypton.enums.Key.Key_unknown
import org.sikuli.script.Mouse
import java.awt.event.MouseEvent

// Extracted from qnamespace.h
/**
 * Represents a mouse button, see [here](http://doc.qt.io/qt-5/qt.html#MouseButton-enum)
 * for more information.
 *
 * @property value A unique value assigned to the enum
 */
enum class MouseButton(val value: Long) {
    NoButton(0x00000000),
    LeftButton(0x00000001),
    RightButton(0x00000002),
    MidButton(0x00000004),
    MiddleButton(MidButton.value),
    BackButton(0x00000008),
    XButton1(BackButton.value),
    ExtraButton1(XButton1.value),
    ForwardButton(0x00000010),
    XButton2(ForwardButton.value),
    ExtraButton2(ForwardButton.value),
    TaskButton(0x00000020),
    ExtraButton3(TaskButton.value),
    ExtraButton4(0x00000040),
    ExtraButton5(0x00000080),
    ExtraButton6(0x00000100),
    ExtraButton7(0x00000200),
    ExtraButton8(0x00000400),
    ExtraButton9(0x00000800),
    ExtraButton10(0x00001000),
    ExtraButton11(0x00002000),
    ExtraButton12(0x00004000),
    ExtraButton13(0x00008000),
    ExtraButton14(0x00010000),
    ExtraButton15(0x00020000),
    ExtraButton16(0x00040000),
    ExtraButton17(0x00080000),
    ExtraButton18(0x00100000),
    ExtraButton19(0x00200000),
    ExtraButton20(0x00400000),
    ExtraButton21(0x00800000),
    ExtraButton22(0x01000000),
    ExtraButton23(0x02000000),
    ExtraButton24(0x04000000),
    AllButtons(0x07ffffff),
    MaxMouseButton(ExtraButton24.value),
    // 4 high-order bits remain available for future use (0x08000000 through 0x40000000).
    MouseButtonMask(0xffffffff);

    companion object {
        private val sikuliMappings = mapOf(
                MouseEvent.NOBUTTON to NoButton,
                Mouse.LEFT to LeftButton,
                Mouse.MIDDLE to MiddleButton,
                Mouse.RIGHT to RightButton
        )

        /**
         * Finds a [MouseButton] with the given value.
         *
         * @param value The value to search for.
         * @return [MouseButton]
         * @throws IllegalStateException If no [MouseButton] was found with the given value.
         */
        fun getForValue(value: Long) =
                values().find { it.value == value } ?: error("No such MouseButton with value $value")

        /**
         * Gets a set of [MouseButton] given a buttons mask formed from [java.awt.event.MouseEvent].
         *
         * @param buttons The [java.awt.event.MouseEvent] mouse button mask.
         * @return A [Set] of [MouseButton], may be empty.
         */
        fun fromSikuliButtons(buttons: Int): Set<MouseButton> {
            return sikuliMappings.filterKeys { it and buttons != 0 }.values.toSet()
        }
    }
}
