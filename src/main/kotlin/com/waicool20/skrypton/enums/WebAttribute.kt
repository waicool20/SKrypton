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

package com.waicool20.skrypton.enums

/**
 * Represents different kinds of settings for a [com.waicool20.skrypton.jni.objects.SKryptonWebSettings]
 * to use, see [here](http://doc.qt.io/qt-5/qwebenginesettings.html#WebAttribute-enum) for more
 * information.
 */
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
