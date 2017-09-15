package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import java.net.URL

class SKryptonWebView(url: String) : QWidget() {
    override val handle: CPointer

    init {
        handle = CPointer(initialize_N(url))
    }

    fun load(url: URL) = load(url.toString())
    fun load(url: String) = load_N(url)

    override fun close() {
        dispose_N()
    }

    private external fun dispose_N()
    private external fun load_N(url: String)
    private external fun initialize_N(url: String): Long
}
