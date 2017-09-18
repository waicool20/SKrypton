package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import java.net.URL

class SKryptonWebView(url: String) : QWidget() {
    override val handle: CPointer

    init {
        handle = CPointer(initialize_N(url))
    }

    var zoomFactor: Double
        get() = zoomFactor_N()
        set(value) = setZoomFactor_N(value)

    fun load(url: URL) = load(url.toString())
    fun load(url: String) = load_N(url)

    fun back() = back_N()
    fun forward() = forward_N()
    fun reload() = reload_N()
    fun stop() = stop_N()

    private fun loadStarted() {
        // TODO loadStarted Listeners
    }

    private fun loadProgress(progress: Int) {
        // TODO loadProgress Listeners
    }

    private fun loadFinished(ok: Boolean) {
        // TODO loadFinished Listeners
    }

    private external fun initialize_N(url: String): Long

    private external fun load_N(url: String)

    private external fun back_N()
    private external fun forward_N()
    private external fun reload_N()
    private external fun stop_N()

    private external fun setZoomFactor_N(factor: Double)
    private external fun zoomFactor_N(): Double
}
