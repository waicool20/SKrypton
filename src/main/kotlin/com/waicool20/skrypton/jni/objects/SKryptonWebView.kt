package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

class SKryptonWebView(url: String) : SKryptonWidget() {
    override val handle: CPointer

    init {
        handle = CPointer(initialize_N(url))
    }

    var zoomFactor: Double
        get() = zoomFactor_N()
        set(value) {
            require(value in 0.25..5.0) {
                "Zoom factor must be a value from 0.25 to 5.0, given value: $value"
            }
            setZoomFactor_N(value)
        }

    val url: String
        get() = url_N()

    val settings by lazy { getSettings_N() }

    var showCursor: Boolean
        get() = isShowingCursor_N()
        set(value) = setShowingCursor_N(value)

    val cursorX: Int
        get() = getCursorX_N()

    val cursorY: Int
        get() = getCursorY_N()

    fun load(url: URL) = load(url.toString())
    fun load(url: String) = load_N(url)
    fun loadHtml(content: String, baseUrl: String) = loadHtml_N(content, baseUrl)

    fun back() = back_N()
    fun forward() = forward_N()
    fun reload() = reload_N()
    fun stop() = stop_N()

    fun isLoading() = isLoading_N()

    //<editor-fold desc="Load listeners">

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

    //</editor-fold>

    fun runJavaScript(content: String, callback: () -> Unit = {}) = runJavaScript_N(content, Runnable { callback() })
    fun takeScreenshot(): BufferedImage {
        return ImageIO.read(takeScreenshot_N().inputStream())
    }

    fun sendEvent(event: SKryptonEvent) = sendEvent_N(event)

    //<editor-fold desc="Native functions">

    private external fun initialize_N(url: String): Long

    private external fun load_N(url: String)
    private external fun loadHtml_N(content: String, baseUrl: String)

    private external fun back_N()
    private external fun forward_N()
    private external fun reload_N()
    private external fun stop_N()

    private external fun isLoading_N(): Boolean

    private external fun getSettings_N(): SKryptonWebSettings

    private external fun setZoomFactor_N(factor: Double)
    private external fun zoomFactor_N(): Double

    private external fun isShowingCursor_N(): Boolean
    private external fun setShowingCursor_N(should: Boolean)

    private external fun getCursorX_N(): Int
    private external fun getCursorY_N(): Int

    private external fun url_N(): String

    private external fun runJavaScript_N(content: String, callback: Runnable)
    private external fun takeScreenshot_N(): ByteArray
    private external fun sendEvent_N(event: SKryptonEvent)
    //</editor-fold>
}
