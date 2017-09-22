package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.enums.Key
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
            type: Int,
            key: Key,
            modifiers: KeyboardModifiers,
            autoRepeat: Boolean,
            count: Int
    ): this(initialize_N(
            type,
            key.code,
            modifiers.value,
            key.code.toChar().toString(),
            autoRepeat,
            count
    ))

    val key by lazy { Key.getForCode(getKey_N()) }
    val character by lazy { getChar_N() }
    val isAutoRepeat by lazy { isAutoRepeat_N() }

    override fun close() = dispose_N()

    private external fun dispose_N()
    private external fun getKey_N(): Long
    private external fun getChar_N(): String
    private external fun isAutoRepeat_N(): Boolean
}
