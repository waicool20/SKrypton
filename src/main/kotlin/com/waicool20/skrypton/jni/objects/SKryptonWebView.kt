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

import com.waicool20.skrypton.jni.CPointer
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

/**
 * A browser window that can be used to view and edit web documents.
 */
class SKryptonWebView(url: String) : SKryptonWidget() {
    /**
     * Native pointer
     */
    override val handle: CPointer

    init {
        handle = CPointer(initialize_N(url))
    }

    /**
     * Current zoom factor of the web view, valid values are 0.25 to 5.0.
     */
    var zoomFactor: Double
        get() = zoomFactor_N()
        /**
         * @throws IllegalArgumentException If given value is not valid.
         */
        set(value) {
            require(value in 0.25..5.0) {
                "Zoom factor must be a value from 0.25 to 5.0, given value: $value"
            }
            setZoomFactor_N(value)
        }

    /**
     * URL of the web page currently viewed.
     */
    val url: String
        get() = url_N()

    /**
     * [SKryptonWebSettings] instance used by this web view. Can be changed without affecting
     * settings of other web view instances.
     */
    val settings by lazy { getSettings_N() }

    /**
     * Whether or not to show the virtual cursor in the web view, useful for when you need to
     * debug mouse events.
     */
    var showCursor: Boolean
        get() = isShowingCursor_N()
        set(value) = setShowingCursor_N(value)

    /**
     * x coordinate of the virtual cursor.
     */
    val cursorX: Int
        get() = getCursorX_N()

    /**
     * y coordinate of the virtual cursor.
     */
    val cursorY: Int
        get() = getCursorY_N()

    /**
     * [SKryptonWebProfile] instance used by this web view. Can be changed without affecting
     * profile of other web view instances.
     */
    val profile by lazy { getProfile_N() }
    // TODO Set Profile?

    //<editor-fold desc="Navigation">
    /**
     * Loads the given url.
     *
     * @param url URL to load.
     */
    fun load(url: URL) = load(url.toString())
    /**
     * Loads the given url.
     *
     * @param url URL string to load.
     */
    fun load(url: String) = load_N(url)
    /**
     * Loads raw HTML content into the web view.
     *
     * @param content HTML content to load.
     * @param baseUrl Stylesheets, images etc. that are referenced in the HTML document will be
     * discovered relative to this location.
     */
    fun loadHtml(content: String, baseUrl: String) = loadHtml_N(content, baseUrl)

    /**
     * Go back to previous page.
     */
    fun back() = back_N()

    /**
     * Go to next page.
     */
    fun forward() = forward_N()

    /**
     * Reloads the current page.
     */
    fun reload() = reload_N()

    /**
     * Stops loading the current page, if it is loading.
     */
    fun stop() = stop_N()

    /**
     * Returns `true` if the current page is still loading.
     */
    fun isLoading() = isLoading_N()

    //</editor-fold>

    //<editor-fold desc="Load listeners">

    //<editor-fold desc="Load start listener">

    private val loadStartedListeners = mutableListOf<() -> Unit>()
    private fun loadStarted() = loadStartedListeners.forEach { it() }

    /**
     * Adds a listener for when the web view starts loading a new page.
     *
     * @param listener Listener used to receive this event.
     */
    fun addOnLoadStartedListener(listener: () -> Unit) {
        loadStartedListeners.add(listener)
    }

    /**
     * Removes given onLoadStarted listener.
     *
     * @param listener Listener used to receive this event.
     */
    fun removeOnLoadStartedListener(listener: () -> Unit) {
        loadStartedListeners.remove(listener)
    }

    //</editor-fold>

    //<editor-fold desc="Load progress listener">

    private val loadProgressListeners = mutableListOf<(progress: Int) -> Unit>()
    private fun loadProgress(progress: Int) = loadProgressListeners.forEach { it(progress) }

    /**
     * Adds a listener for the progress of loading a new page.
     *
     * @param listener Listener used to receive this event. This lambda receives a variable
     * `progress` which holds the current progress state.
     */
    fun addOnLoadProgressListener(listener: (progress: Int) -> Unit) {
        loadProgressListeners.add(listener)
    }

    /**
     * Removes given onLoadProgress listener.
     *
     * @param listener Listener used to receive this event.
     */
    fun removeOnLoadProgressListener(listener: (progress: Int) -> Unit) {
        loadProgressListeners.remove(listener)
    }

    //</editor-fold>

    //<editor-fold desc="Load finished listener">

    private val loadFinishedListeners = mutableListOf<(ok: Boolean) -> Unit>()
    private fun loadFinished(ok: Boolean) = loadFinishedListeners.forEach { it(ok) }

    /**
     * Adds a listener for when the web view is done loading a page.
     *
     * @param listener Listener used to receive this event. This lambda receives a variable
     * `ok` which holds the result of the page loading.
     */
    fun addOnLoadFinishedListener(listener: (ok: Boolean) -> Unit) {
        loadFinishedListeners.add(listener)
    }

    /**
     * Removes given onLoadFinished listener.
     *
     * @param listener Listener used to receive this event.
     */
    fun removeOnLoadFinishedListener(listener: (ok: Boolean) -> Unit) {
        loadFinishedListeners.remove(listener)
    }

    //</editor-fold>

    //</editor-fold>

    /**
     * Runs JavaScript content on this web view.
     *
     * @param content JavaScript code to run.
     * @param callback Executed when the given JavaScript code is done executing.
     */
    fun runJavaScript(content: String, callback: () -> Unit = {})
            = runJavaScript_N(content, Runnable { callback() })

    /**
     * Takes a screenshot of the web views contents.
     *
     * @return The screenshot image.
     */
    fun takeScreenshot(): BufferedImage {
        return ImageIO.read(takeScreenshot_N().inputStream())
    }

    /**
     * Sends the given event to the web view
     *
     * @param event The event to send.
     */
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

    private external fun getProfile_N(): SKryptonWebProfile

    private external fun url_N(): String

    private external fun runJavaScript_N(content: String, callback: Runnable)
    private external fun takeScreenshot_N(): ByteArray
    private external fun sendEvent_N(event: SKryptonEvent)
    //</editor-fold>
}

/**
 * Creates a web view under this [SKryptonApp] instance.
 *
 * @receiver [SKryptonApp]
 * @param url URL string to load initially.
 * @param action Lambda function with created web view as its receiver.
 */
fun SKryptonApp.webView(url: String, action: SKryptonWebView.() -> Unit = {}): SKryptonWebView {
    return SKryptonWebView(url).apply { action() }
}
