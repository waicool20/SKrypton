/*
 * The MIT License (MIT)
 *
 * Copyright (c) skrypton-api by waicool20
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

import com.waicool20.skrypton.enums.HttpCacheType
import com.waicool20.skrypton.enums.PersistentCookiesPolicy
import com.waicool20.skrypton.jni.CPointer
import com.waicool20.skrypton.jni.NativeInterface
import com.waicool20.skrypton.util.loggerFor
import java.nio.file.Path
import java.nio.file.Paths

class SKryptonWebProfile private constructor(pointer: Long) : NativeInterface() {
    private val logger = loggerFor<SKryptonWebProfile>()
    override val handle = CPointer(pointer)

    companion object {
        val defaults by lazy { defaultProfile_N() }
        private external fun defaultProfile_N(): SKryptonWebProfile
    }

    //<editor-fold desc="Member values">

    var cachePath: Path
        get() = Paths.get(getCachePath_N())
        set(value) = setCachePath_N(value.toString())
    var httpAcceptLanguage: String
        get() = getHttpAcceptLanguage_N()
        set(value) = setHttpAcceptLanguage_N(value)
    var httpCacheMaxSize: Int
        get() = getHttpCacheMaxSize_N()
        set(value) = setHttpCacheMaxSize_N(value)
    var httpCacheType: HttpCacheType
        get() = HttpCacheType.values()[getHttpCacheType_N()]
        set(value) = setHttpCacheType_N(value.ordinal)
    var httpUserAgent: String
        get() = getHttpUserAgent_N()
        set(value) = setHttpUserAgent_N(value)
    var persistentCookiesPolicy: PersistentCookiesPolicy
        get() = PersistentCookiesPolicy.values()[getPersistentCookiesPolicy_N()]
        set(value) = setPersistentCookiesPolicy_N(value.ordinal)
    var persistentStoragePath: Path
        get() = Paths.get(getPersistentStoragePath_N())
        set(value) = setPersistentStoragePath_N(value.toString())
    var spellCheckEnabled: Boolean
        get() = isSpellCheckEnabled_N()
        set(value) = setSpellCheckEnabled_N(value)
    var spellCheckLanguages: Array<String>
        get() = getSpellCheckLanguages_N()
        set(value) = setSpellCheckLanguages_N(value)

    val isOffTheRecord: Boolean
        get() = isOffTheRecord_N()
    val storageName: String
        get() = getStorageName_N()

    //</editor-fold>

    fun clearAllVisitedLinks() = clearAllVisitedLinks_N()
    fun clearHttpCache() = clearHttpCache_N()
    fun clearVisitedLinks(urls: List<String>) = clearVisitedLinks(urls.toTypedArray())

    fun visitedLinksContainsUrl(url: String) = visitedLinksContainsUrl_N(url)

    // TODO cookieStore()
    // TODO installUrlSchemeHandler()
    // TODO removeAllUrlSchemeHandlers()
    // TODO removeUrlScheme()
    // TODO removeUrlSchemeHandler() maybe?
    // TODO getUrlSchemeHandler() maybe?

    //<editor-fold desc="Native functions">

    private external fun getCachePath_N(): String
    private external fun setCachePath_N(path: String)
    private external fun getHttpAcceptLanguage_N(): String
    private external fun setHttpAcceptLanguage_N(language: String)
    private external fun getHttpCacheMaxSize_N(): Int
    private external fun setHttpCacheMaxSize_N(size: Int)
    private external fun getHttpCacheType_N(): Int
    private external fun setHttpCacheType_N(type: Int)
    private external fun getHttpUserAgent_N(): String
    private external fun setHttpUserAgent_N(agent: String)
    private external fun getPersistentCookiesPolicy_N(): Int
    private external fun setPersistentCookiesPolicy_N(policy: Int)
    private external fun getPersistentStoragePath_N(): String
    private external fun setPersistentStoragePath_N(path: String)
    private external fun isSpellCheckEnabled_N(): Boolean
    private external fun setSpellCheckEnabled_N(enable: Boolean)
    private external fun getSpellCheckLanguages_N(): Array<String>
    private external fun setSpellCheckLanguages_N(languages: Array<String>)

    private external fun clearAllVisitedLinks_N()
    private external fun clearHttpCache_N()
    private external fun clearVisitedLinks(urls: Array<String>)

    private external fun visitedLinksContainsUrl_N(url: String): Boolean

    private external fun isOffTheRecord_N(): Boolean
    private external fun getStorageName_N(): String
    //</editor-fold>
    override fun close() {
        logger.error { "Profile cannot be disposed, dispose related WebView instead" }
    }
}
