package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import com.waicool20.skrypton.jni.NativeInterface
import com.waicool20.skrypton.util.loggerFor
import java.nio.charset.Charset


class SKryptonWebSettings private constructor(pointer: Long) : NativeInterface() {
    private val logger = loggerFor<SKryptonWebSettings>()
    override val handle: CPointer = CPointer(pointer)

    companion object {
        val defaults by lazy { defaultSettings_N() }
        private external fun defaultSettings_N(): SKryptonWebSettings
    }

    //<editor-fold desc="Attribute member fields">

    var autoLoadImages
        get() = testAttribute_N(WebAttribute.AutoLoadImages.ordinal)
        set(value) = setAttribute_N(WebAttribute.AutoLoadImages.ordinal, value)
    var javascriptEnabled
        get() = testAttribute_N(WebAttribute.JavascriptEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.JavascriptEnabled.ordinal, value)
    var javascriptCanOpenWindows
        get() = testAttribute_N(WebAttribute.JavascriptCanOpenWindows.ordinal)
        set(value) = setAttribute_N(WebAttribute.JavascriptCanOpenWindows.ordinal, value)
    var javascriptCanAccessClipboard
        get() = testAttribute_N(WebAttribute.JavascriptCanAccessClipboard.ordinal)
        set(value) = setAttribute_N(WebAttribute.JavascriptCanAccessClipboard.ordinal, value)
    var linksIncludedInFocusChain
        get() = testAttribute_N(WebAttribute.LinksIncludedInFocusChain.ordinal)
        set(value) = setAttribute_N(WebAttribute.LinksIncludedInFocusChain.ordinal, value)
    var localStorageEnabled
        get() = testAttribute_N(WebAttribute.LocalStorageEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.LocalStorageEnabled.ordinal, value)
    var localContentCanAccessRemoteUrls
        get() = testAttribute_N(WebAttribute.LocalContentCanAccessRemoteUrls.ordinal)
        set(value) = setAttribute_N(WebAttribute.LocalContentCanAccessRemoteUrls.ordinal, value)
    var xssAuditingEnabled
        get() = testAttribute_N(WebAttribute.XssAuditingEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.XssAuditingEnabled.ordinal, value)
    var spatialNavigationEnabled
        get() = testAttribute_N(WebAttribute.SpatialNavigationEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.SpatialNavigationEnabled.ordinal, value)
    var localContentCanAccessFileUrls
        get() = testAttribute_N(WebAttribute.LocalContentCanAccessFileUrls.ordinal)
        set(value) = setAttribute_N(WebAttribute.LocalContentCanAccessFileUrls.ordinal, value)
    var hyperlinkAuditingEnabled
        get() = testAttribute_N(WebAttribute.HyperlinkAuditingEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.AutoLoadImages.ordinal, value)
    var scrollAnimatorEnabled
        get() = testAttribute_N(WebAttribute.ScrollAnimatorEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.ScrollAnimatorEnabled.ordinal, value)
    var errorPageEnabled
        get() = testAttribute_N(WebAttribute.ErrorPageEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.ErrorPageEnabled.ordinal, value)
    var pluginsEnabled
        get() = testAttribute_N(WebAttribute.PluginsEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.PluginsEnabled.ordinal, value)
    var fullScreenSupportEnabled
        get() = testAttribute_N(WebAttribute.FullScreenSupportEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.FullScreenSupportEnabled.ordinal, value)
    var screenCaptureEnabled
        get() = testAttribute_N(WebAttribute.ScreenCaptureEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.ScreenCaptureEnabled.ordinal, value)
    var webGlEnabled
        get() = testAttribute_N(WebAttribute.WebGlEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.WebGlEnabled.ordinal, value)
    var accelerated2dCanvasEnabled
        get() = testAttribute_N(WebAttribute.Accelerated2dCanvasEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.Accelerated2dCanvasEnabled.ordinal, value)
    var autoLoadIconsForPage
        get() = testAttribute_N(WebAttribute.AutoLoadIconsForPage.ordinal)
        set(value) = setAttribute_N(WebAttribute.AutoLoadIconsForPage.ordinal, value)
    var touchIconsEnabled
        get() = testAttribute_N(WebAttribute.TouchIconsEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.TouchIconsEnabled.ordinal, value)
    var focusOnNavigationEnabled
        get() = testAttribute_N(WebAttribute.FocusOnNavigationEnabled.ordinal)
        set(value) = setAttribute_N(WebAttribute.FocusOnNavigationEnabled.ordinal, value)
    var printElementBackgrounds
        get() = testAttribute_N(WebAttribute.PrintElementBackgrounds.ordinal)
        set(value) = setAttribute_N(WebAttribute.PrintElementBackgrounds.ordinal, value)
    var allowRunningInsecureContent
        get() = testAttribute_N(WebAttribute.AllowRunningInsecureContent.ordinal)
        set(value) = setAttribute_N(WebAttribute.AllowRunningInsecureContent.ordinal, value)
    var allowGeolocationOnInsecureOrigins
        get() = testAttribute_N(WebAttribute.AllowGeolocationOnInsecureOrigins.ordinal)
        set(value) = setAttribute_N(WebAttribute.AllowGeolocationOnInsecureOrigins.ordinal, value)

    //</editor-fold>

    //<editor-fold desc="Font member fields>

    var defaultTextEncoding
        get() = Charset.forName(getDefaultTextEncoding_N())
        set(value) = setDefaultTextEncoding_N(value.name())

    //</editor-fold>

    enum class FontFamily {
        StandardFont, FixedFont, SerifFont,
        SansSerifFont, CursiveFont, FantasyFont,
        PictographFint
    }

    enum class FontSize {
        MinimumFontSize, MinimumLogicalFontSize,
        DefaultFontSize, DefaultFixedFontSize
    }

    enum class WebAttribute {
        AutoLoadImages,
        JavascriptEnabled,
        JavascriptCanOpenWindows,
        JavascriptCanAccessClipboard,
        LinksIncludedInFocusChain,
        LocalStorageEnabled,
        LocalContentCanAccessRemoteUrls,
        XssAuditingEnabled,
        SpatialNavigationEnabled,
        LocalContentCanAccessFileUrls,
        HyperlinkAuditingEnabled,
        ScrollAnimatorEnabled,
        ErrorPageEnabled,
        PluginsEnabled,
        FullScreenSupportEnabled,
        ScreenCaptureEnabled,
        WebGlEnabled,
        Accelerated2dCanvasEnabled,
        AutoLoadIconsForPage,
        TouchIconsEnabled,
        FocusOnNavigationEnabled,
        PrintElementBackgrounds,
        AllowRunningInsecureContent,
        AllowGeolocationOnInsecureOrigins
    }

    fun resetAttribute(attribute: WebAttribute) = resetAttribute_N(attribute.ordinal)

    override fun close() {
        logger.info { "Settings cannot be disposed, dispose related WebView instead" }
    }

    private external fun getDefaultTextEncoding_N(): String
    private external fun setDefaultTextEncoding_N(string: String)

    private external fun resetAttribute_N(attribute: Int)
    private external fun setAttribute_N(attribute: Int, enabled: Boolean)
    private external fun testAttribute_N(attribute: Int): Boolean
}
