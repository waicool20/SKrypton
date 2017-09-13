package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import com.waicool20.skrypton.jni.NativeInterface

class SKryptonWebView private constructor(override val handle: CPointer) : NativeInterface() {

    companion object Factory {
        fun createNew(url: String) = SKryptonWebView(CPointer(initialize_N(url)))
        private external fun initialize_N(url: String): Long
    }

    override fun close() {
        dispose_N()
    }

    private external fun dispose_N()

}
