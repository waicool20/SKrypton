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

import com.waicool20.skrypton.enums.FontFamily
import com.waicool20.skrypton.enums.FontSize
import com.waicool20.skrypton.enums.WebAttribute
import com.waicool20.skrypton.jni.CPointer
import com.waicool20.skrypton.jni.NativeInterface
import com.waicool20.skrypton.util.loggerFor
import java.nio.charset.Charset

/**
 * A class representing settings used by the backing web engine.
 */
class SKryptonWebSettings private constructor(pointer: Long) : NativeInterface() {
    private val logger = loggerFor<SKryptonWebSettings>()
    /**
     * Native pointer
     */
    override val handle: CPointer = CPointer(pointer)

    companion object {
        /**
         * Settings instance that is used by default.
         */
        val defaults by lazy { defaultSettings_N() }

        private external fun defaultSettings_N(): SKryptonWebSettings
    }

    //<editor-fold desc="Attribute member fields">

    /**
     * Automatically downloads images for web pages. When this setting is disabled, images are
     * loaded from the cache. Enabled by default.
     */
    var autoLoadImages
        get() = testAttribute_N(WebAttribute.AutoLoadImages.ordinal)
        set(value) = setAttribute_N(WebAttribute.AutoLoadImages.ordinal, value)
    /**
     * Enables the running of JavaScript programs. Enabled by default.
     */
    var javascriptEnabled
        get() = testAttribute_N(WebAttribute.JavascriptEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.JavascriptEnabled.ordinal, value)
    /**
     * Allows JavaScript programs to open popup windows without user interaction. Enabled by default.
     */
    var javascriptCanOpenWindows
        get() = testAttribute_N(WebAttribute.JavascriptCanOpenWindows.ordinal)
        set(value) = setAttribute_N(WebAttribute.JavascriptCanOpenWindows.ordinal, value)
    /**
     * Allows JavaScript programs to read from and write to the clipboard. Writing to the clipboard
     * is always allowed if it is specifically requested by the user. Disabled by default.
     */
    var javascriptCanAccessClipboard
        get() = testAttribute_N(WebAttribute.JavascriptCanAccessClipboard.ordinal)
        set(value) = setAttribute_N(WebAttribute.JavascriptCanAccessClipboard.ordinal, value)
    /**
     * Includes hyperlinks in the keyboard focus chain. Enabled by default.
     */
    var linksIncludedInFocusChain
        get() = testAttribute_N(WebAttribute.LinksIncludedInFocusChain.ordinal)
        set(value) = setAttribute_N(WebAttribute.LinksIncludedInFocusChain.ordinal, value)
    /**
     * Enables support for the HTML 5 local storage feature. Enabled by default.
     */
    var localStorageEnabled
        get() = testAttribute_N(WebAttribute.LocalStorageEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.LocalStorageEnabled.ordinal, value)
    /**
     * Allows locally loaded documents to ignore cross-origin rules so that they can access remote
     * resources that would normally be blocked, because all remote resources are considered
     * cross-origin for a local file. Remote access that would not be blocked by cross-origin rules
     * is still possible when this setting is disabled. Note that disabling this setting does not
     * stop XMLHttpRequests or media elements in local files from accessing remote content.
     * Basically, it only stops some HTML subresources, such as scripts, and therefore disabling
     * this setting is not a safety mechanism. Disabled by default.
     */
    var localContentCanAccessRemoteUrls
        get() = testAttribute_N(WebAttribute.LocalContentCanAccessRemoteUrls.ordinal)
        set(value) = setAttribute_N(WebAttribute.LocalContentCanAccessRemoteUrls.ordinal, value)
    /**
     * Monitors load requests for cross-site scripting attempts. Suspicious scripts are blocked and
     * reported in the inspector's JavaScript console. Disabled by default, because it might
     * negatively affect performance.
     */
    var xssAuditingEnabled
        get() = testAttribute_N(WebAttribute.XssAuditingEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.XssAuditingEnabled.ordinal, value)
    /**
     * Enables the Spatial Navigation feature, which means the ability to navigate between focusable
     * elements, such as hyperlinks and form controls, on a web page by using the Left, Right, Up
     * and Down arrow keys. For example, if a user presses the Right key, heuristics determine
     * whether there is an element they might be trying to reach towards the right and which
     * element they probably want. Disabled by default.
     */
    var spatialNavigationEnabled
        get() = testAttribute_N(WebAttribute.SpatialNavigationEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.SpatialNavigationEnabled.ordinal, value)
    /**
     * Allows locally loaded documents to access other local URLs. Enabled by default.
     */
    var localContentCanAccessFileUrls
        get() = testAttribute_N(WebAttribute.LocalContentCanAccessFileUrls.ordinal)
        set(value) = setAttribute_N(WebAttribute.LocalContentCanAccessFileUrls.ordinal, value)
    /**
     * Enables support for the ping attribute for hyperlinks. Disabled by default.
     */
    var hyperlinkAuditingEnabled
        get() = testAttribute_N(WebAttribute.HyperlinkAuditingEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.AutoLoadImages.ordinal, value)
    /**
     * Enables animated scrolling. Disabled by default.
     */
    var scrollAnimatorEnabled
        get() = testAttribute_N(WebAttribute.ScrollAnimatorEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.ScrollAnimatorEnabled.ordinal, value)
    /**
     * Enables displaying the built-in error pages of Chromium. Enabled by default.
     */
    var errorPageEnabled
        get() = testAttribute_N(WebAttribute.ErrorPageEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.ErrorPageEnabled.ordinal, value)
    /**
     * Enables support for Pepper plugins, such as the Flash player. Disabled by default.
     */
    var pluginsEnabled
        get() = testAttribute_N(WebAttribute.PluginsEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.PluginsEnabled.ordinal, value)
    /**
     * Enables fullscreen support in an application. Disabled by default.
     */
    var fullScreenSupportEnabled
        get() = testAttribute_N(WebAttribute.FullScreenSupportEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.FullScreenSupportEnabled.ordinal, value)
    /**
     * Enables screen capture in an application. Disabled by default.
     */
    var screenCaptureEnabled
        get() = testAttribute_N(WebAttribute.ScreenCaptureEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.ScreenCaptureEnabled.ordinal, value)
    /**
     * Enables support for HTML 5 WebGL. Enabled by default if available.
     */
    var webGlEnabled
        get() = testAttribute_N(WebAttribute.WebGlEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.WebGlEnabled.ordinal, value)
    /**
     * Specifies whether the HTML5 2D canvas should be a OpenGL framebuffer. This makes many
     * painting operations faster, but slows down pixel access. Enabled by default if available.
     */
    var accelerated2dCanvasEnabled
        get() = testAttribute_N(WebAttribute.Accelerated2dCanvasEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.Accelerated2dCanvasEnabled.ordinal, value)
    /**
     * Automatically downloads icons for web pages. Enabled by default.
     */
    var autoLoadIconsForPage
        get() = testAttribute_N(WebAttribute.AutoLoadIconsForPage.ordinal)
        set(value) = setAttribute_N(WebAttribute.AutoLoadIconsForPage.ordinal, value)
    /**
     * Enables support for touch icons and precomposed touch icons Disabled by default.
     */
    var touchIconsEnabled
        get() = testAttribute_N(WebAttribute.TouchIconsEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.TouchIconsEnabled.ordinal, value)
    /**
     * Gives focus to the view associated with the page, whenever a navigation operation occurs
     * (load, stop, reload, reload and bypass cache, forward, backward, set content, and so on).
     * Enabled by default.
     */
    var focusOnNavigationEnabled
        get() = testAttribute_N(WebAttribute.FocusOnNavigationEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.FocusOnNavigationEnabled.ordinal, value)
    /**
     * Turns on printing of CSS backgrounds when printing a web page. Enabled by default.
     */
    var printElementBackgrounds
        get() = testAttribute_N(WebAttribute.PrintElementBackgrounds.ordinal)
        set(value) = setAttribute_N(WebAttribute.PrintElementBackgrounds.ordinal, value)
    /**
     * By default, HTTPS pages cannot run JavaScript, CSS, plugins or web-sockets from HTTP URLs.
     * This provides an override to get the old insecure behavior. Disabled by default.
     */
    var allowRunningInsecureContent
        get() = testAttribute_N(WebAttribute.AllowRunningInsecureContent.ordinal)
        set(value) = setAttribute_N(WebAttribute.AllowRunningInsecureContent.ordinal, value)
    /**
     * Since Qt 5.7, only secure origins such as HTTPS have been able to request Geolocation
     * features. This provides an override to allow non secure origins to access Geolocation again.
     * Disabled by default.
     */
    var allowGeolocationOnInsecureOrigins
        get() = testAttribute_N(WebAttribute.AllowGeolocationOnInsecureOrigins.ordinal)
        set(value) = setAttribute_N(WebAttribute.AllowGeolocationOnInsecureOrigins.ordinal, value)

    //</editor-fold>

    /**
     * Default text encoding used by the web engine.
     */
    var defaultTextEncoding
        get() = Charset.forName(getDefaultTextEncoding_N())
        set(value) = setDefaultTextEncoding_N(value.name())

    /**
     * Resets the given attribute.
     *
     * @param attribute The attribute to reset.
     */
    fun resetAttribute(attribute: WebAttribute) = resetAttribute_N(attribute.ordinal)

    /**
     * Gets the default font size.
     *
     * @param font The type of FontSize
     */
    fun getFontSize(font: FontSize) = getFontSize_N(font.ordinal)
    /**
     * Sets the default font size.
     *
     * @param font The type of FontSize
     * @param size The size of font in pixels
     */
    fun setFontSize(font: FontSize, size: Int) = setFontSize_N(font.ordinal, size)
    /**
     * Resets the default font size.
     */
    fun resetFontSize(font: FontSize) = resetFontSize_N(font.ordinal)

    /**
     * Gets the actual font family for the specified generic font family.
     *
     * @param family The type of FontFamily
     */
    fun getFontFamily(family: FontFamily) = getFontFamily_N(family.ordinal)
    /**
     * Sets the actual font family for the specified generic font family.
     *
     * @param whichFamily The type of FontFamily
     * @param family The family to set the FontFamily to.
     */
    fun setFontFamily(whichFamily: FontFamily, family: String) = setFontFamily_N(whichFamily.ordinal, family)
    /**
     * Resets the default font family.
     */
    fun resetFontFamily(family: FontFamily) = resetFontFamily_N(family.ordinal)

    /**
     * Doesn't do anything, dispose the web view using this settings instead.
     */
    override fun close() {
        logger.error { "Settings cannot be disposed, dispose related WebView instead" }
    }

    private external fun getFontSize_N(font: Int): Int
    private external fun setFontSize_N(font: Int, size: Int)
    private external fun resetFontSize_N(font: Int)

    private external fun getFontFamily_N(family: Int): String
    private external fun setFontFamily_N(whichFamily: Int, family: String)
    private external fun resetFontFamily_N(family: Int)

    private external fun getDefaultTextEncoding_N(): String
    private external fun setDefaultTextEncoding_N(string: String)

    private external fun resetAttribute_N(attribute: Int)
    private external fun setAttribute_N(attribute: Int, enabled: Boolean)
    private external fun testAttribute_N(attribute: Int): Boolean
}
