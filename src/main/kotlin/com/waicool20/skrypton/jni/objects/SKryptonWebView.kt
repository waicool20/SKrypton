package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

class SKryptonWebView(url: String) : QWidget() {
    override val handle: CPointer

    init {
        handle = CPointer(initialize_N(url))
    }

    var zoomFactor: Double
        get() = zoomFactor_N()
        set(value) = setZoomFactor_N(value)

    val settings: SKryptonWebSettings
        get() = getSettings_N()

    fun load(url: URL) = load(url.toString())
    fun load(url: String) = load_N(url)

    fun back() = back_N()
    fun forward() = forward_N()
    fun reload() = reload_N()
    fun stop() = stop_N()

    //<editor-fold desc="Load start listener">

    private val loadStartedListeners = mutableListOf<() -> Unit>()
    private fun loadStarted() = loadStartedListeners.forEach { it() }

    fun addOnLoadStartedListener(listener: () -> Unit) {
        loadStartedListeners.add(listener)
    }

    fun removeOnLoadStartedListener(listener: () -> Unit) {
        loadStartedListeners.remove(listener)
    }

    //</editor-fold>

    //<editor-fold desc="Load progress listener">

    private val loadProgressListeners = mutableListOf<(progress: Int) -> Unit>()
    private fun loadProgress(progress: Int) = loadProgressListeners.forEach { it(progress) }

    fun addOnLoadProgressListener(listener: (progress: Int) -> Unit) {
        loadProgressListeners.add(listener)
    }

    fun removeOnLoadProgressListener(listener: (progress: Int) -> Unit) {
        loadProgressListeners.remove(listener)
    }

    //</editor-fold>

    //<editor-fold desc="Load finished listener">

    private val loadFinishedListeners = mutableListOf<(ok: Boolean) -> Unit>()
    private fun loadFinished(ok: Boolean) = loadFinishedListeners.forEach { it(ok) }

    fun addOnLoadFinishedListener(listener: (ok: Boolean) -> Unit) {
        loadFinishedListeners.add(listener)
    }

    fun removeOnLoadFinishedListener(listener: (ok: Boolean) -> Unit) {
        loadFinishedListeners.remove(listener)
    }

    //</editor-fold>

    fun takeScreenshot(): BufferedImage {
        return ImageIO.read(takeScreenshot_N().inputStream())
    }
    //<editor-fold desc="Native functions">

    private external fun initialize_N(url: String): Long

    private external fun load_N(url: String)

    private external fun back_N()
    private external fun forward_N()
    private external fun reload_N()
    private external fun stop_N()

    private external fun getSettings_N(): SKryptonWebSettings

    private external fun setZoomFactor_N(factor: Double)
    private external fun zoomFactor_N(): Double

    private external fun takeScreenshot_N(): ByteArray
    //</editor-fold>
}
