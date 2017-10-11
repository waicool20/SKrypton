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

import com.waicool20.skrypton.enums.Key
import com.waicool20.skrypton.enums.KeyEventType
import com.waicool20.skrypton.enums.KeyboardModifiers
import com.waicool20.skrypton.jni.CPointer


class SKryptonKeyEvent private constructor(pointer: Long) : SKryptonEvent(pointer) {
    private companion object {
        private external fun initialize_N(
                type: Int,
                key: Long,
                modifiers: Long,
                character: String,
                autoRepeat: Boolean,
                count: Int
        ): Long
    }

    constructor(
            type: KeyEventType,
            key: Key,
            modifiers: KeyboardModifiers = KeyboardModifiers.NoModifier,
            autoRepeat: Boolean = false,
            count: Int = 1
    ) : this(initialize_N(
            type.id, key.code, modifiers.value, key.code.toChar().toString(), autoRepeat, count
    ))

    constructor(
            type: KeyEventType,
            char: Char,
            modifiers: KeyboardModifiers = KeyboardModifiers.NoModifier,
            autoRepeat: Boolean = false,
            count: Int = 1
    ) : this(initialize_N(
            type.id, Key.getForCode(char.toLong()).code, modifiers.value, char.toString(), autoRepeat, count
    ))

    constructor(
            type: KeyEventType,
            char: String,
            modifiers: KeyboardModifiers = KeyboardModifiers.NoModifier,
            autoRepeat: Boolean = false,
            count: Int = 1
    ) : this(type, char.toCharArray().first(), modifiers, autoRepeat, count) {
        require(char.length == 1) { "Only 1 character allowed" }
    }

    val key by lazy { Key.getForCode(getKey_N()) }
    val character by lazy { getChar_N() }
    val isAutoRepeat by lazy { isAutoRepeat_N() }

    private external fun getKey_N(): Long
    private external fun getChar_N(): String
    private external fun isAutoRepeat_N(): Boolean
}
