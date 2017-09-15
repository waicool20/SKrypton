package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import java.net.URL

class SKryptonWebView private constructor(override val handle: CPointer) : QWidget() {

    companion object Factory {
        fun createNew(url: String) = SKryptonWebView(CPointer(initialize_N(url)))
        private external fun initialize_N(url: String): Long
    }

    fun load(url: URL) = load(url.toString())
    fun load(url: String) = load_N(url)

    override fun close() {
        dispose_N()
    }

    private external fun dispose_N()
    private external fun load_N(url: String)
}
