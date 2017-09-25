package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.NativeInterface

abstract class SKryptonEvent : NativeInterface() {
    override fun close() = dispose_N()

    private external fun dispose_N()
}
