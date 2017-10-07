#include <SKryptonWebProfile.h>

JNIEXPORT jobject JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_00024Companion_defaultProfile_1N(JNIEnv* env, jobject obj) {
    auto profilePointer = (jlong) QWebEngineProfile::defaultProfile();
    auto jProfile = NewObject(env, "com.waicool20.skrypton.jni.objects.SKryptonWebProfile", "(J)V", profilePointer);
    if (jProfile) return jProfile.value();
    ThrowNewError(env, LOG_PREFIX + "Could not retrieve default webview profile");
    return {};
}

JNIEXPORT jstring JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_getCachePath_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        return JstringFromString(env, profile->cachePath().toStdString());
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get cache path");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setCachePath_1N(JNIEnv* env, jobject obj, jstring path) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        profile->setCachePath(QString::fromStdString(StringFromJstring(env, path)));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set cache path");
    }
}

JNIEXPORT jstring JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_getHttpAcceptLanguage_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        return JstringFromString(env, profile->httpAcceptLanguage().toStdString());
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get Http Accept-Language");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setHttpAcceptLanguage_1N(JNIEnv* env, jobject obj, jstring language) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        profile->setHttpAcceptLanguage(QString::fromStdString(StringFromJstring(env, language)));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set Http Accept-Language");
    }
}

JNIEXPORT jint JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_getHttpCacheMaxSize_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        return profile->httpCacheMaximumSize();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get Http cache maximum size");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setHttpCacheMaxSize_1N(JNIEnv* env, jobject obj, jint size) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        profile->setHttpCacheMaximumSize(size);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set Http cache maximum size");
    }
}

JNIEXPORT jint JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_getHttpCacheType_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        return profile->httpCacheType();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get Http cache type");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setHttpCacheType_1N(JNIEnv* env, jobject obj, jint e_HttpCacheType) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        auto type = static_cast<QWebEngineProfile::HttpCacheType>(e_HttpCacheType);
        profile->setHttpCacheType(type);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set Http cache type");
    }
}

JNIEXPORT jstring JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_getHttpUserAgent_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        return JstringFromString(env, profile->httpUserAgent().toStdString());
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get Http user agent");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setHttpUserAgent_1N(JNIEnv* env, jobject obj, jstring agent) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        profile->setHttpUserAgent(QString::fromStdString(StringFromJstring(env, agent)));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set Http user agent");
    }
}

