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

// Extracted from qnamespace.h
/**
 * Represents a keyboard modifier, see [here](http://doc.qt.io/qt-5/qt.html#KeyboardModifier-enum)
 * for more information.
 *
 * @property value A unique value assigned to the enum
 */
enum class KeyboardModifiers(val value: Long) {
    NoModifier(0x00000000),
    ShiftModifier(0x02000000),
    ControlModifier(0x04000000),
    AltModifier(0x08000000),
    MetaModifier(0x10000000),
    KeypadModifier(0x20000000),
    GroupSwitchModifier(0x40000000),
    // Do not extend the mask to include 0x01000000
    KeyboardModifierMask(0xfe000000)
}
