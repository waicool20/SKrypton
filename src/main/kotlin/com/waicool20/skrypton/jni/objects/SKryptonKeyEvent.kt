package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.enums.Key
import com.waicool20.skrypton.enums.KeyEventType
import com.waicool20.skrypton.enums.KeyboardModifiers
import com.waicool20.skrypton.jni.CPointer


class SKryptonKeyEvent private constructor(pointer: Long): SKryptonEvent() {
    override val handle = CPointer(pointer)

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
    ): this(initialize_N(
            type.id, key.code, modifiers.value, key.code.toChar().toString(), autoRepeat, count
    ))

    constructor(
            type: KeyEventType,
            char: Char,
            modifiers: KeyboardModifiers = KeyboardModifiers.NoModifier,
            autoRepeat: Boolean = false,
            count: Int = 1
    ): this(initialize_N(
            type.id, Key.getForCode(char.toLong()).code, modifiers.value, char.toString(),  autoRepeat, count
    ))

    constructor(
            type: KeyEventType,
            char: String,
            modifiers: KeyboardModifiers = KeyboardModifiers.NoModifier,
            autoRepeat: Boolean = false,
            count: Int = 1
    ): this(type, char.toCharArray().first(), modifiers, autoRepeat, count) {
        require(char.length == 1) { "Only 1 character allowed" }
    }

    val key by lazy { Key.getForCode(getKey_N()) }
    val character by lazy { getChar_N() }
    val isAutoRepeat by lazy { isAutoRepeat_N() }

    override fun close() = dispose_N()

    private external fun dispose_N()
    private external fun getKey_N(): Long
    private external fun getChar_N(): String
    private external fun isAutoRepeat_N(): Boolean
}
