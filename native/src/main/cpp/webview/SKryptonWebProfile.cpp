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
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setHttpAcceptLanguage_1N(JNIEnv* env, jobject obj,
                                                                                    jstring language) {
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
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setHttpCacheType_1N(JNIEnv* env, jobject obj,
                                                                               jint e_HttpCacheType) {
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
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setHttpUserAgent_1N(JNIEnv* env, jobject obj,
                                                                               jstring agent) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        profile->setHttpUserAgent(QString::fromStdString(StringFromJstring(env, agent)));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set Http user agent");
    }
}

JNIEXPORT jint JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_getPersistentCookiesPolicy_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        return profile->persistentCookiesPolicy();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get persistent cookies policy");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setPersistentCookiesPolicy_1N(JNIEnv* env, jobject obj,
                                                                                         jint e_policy) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        auto policy = static_cast<QWebEngineProfile::PersistentCookiesPolicy>(e_policy);
        profile->setPersistentCookiesPolicy(policy);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set persistent cookies policy");
    }
}

JNIEXPORT jstring JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_getPersistentStoragePath_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        return JstringFromString(env, profile->persistentStoragePath().toStdString());
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get persistent storage path");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setPersistentStoragePath_1N(JNIEnv* env, jobject obj,
                                                                                       jstring path) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        profile->setPersistentStoragePath(QString::fromStdString(StringFromJstring(env, path)));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set persistent storage path");
    }
}

JNIEXPORT jboolean JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_isSpellCheckEnabled_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        return profile->isSpellCheckEnabled();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not check if spell check enabled");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setSpellCheckEnabled_1N(JNIEnv* env, jobject obj,
                                                                                   jboolean enable) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        profile->setSpellCheckEnabled(enable);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set spell check enabled");
    }
}

JNIEXPORT jobjectArray JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_getSpellCheckLanguages_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        auto languages = profile->spellCheckLanguages();
        auto stringClass = FindClass("java.lang.String");
        if (stringClass) {
            auto arr = env->NewObjectArray(languages.size(), stringClass.value(), env->NewStringUTF(""));
            for (int i = 0; i < languages.size(); i++) {
                env->SetObjectArrayElement(arr, i, JstringFromString(env, languages.at(i).toStdString()));
            }
            return arr;
        }
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get spell check languages");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebProfile_setSpellCheckLanguages_1N(JNIEnv* env, jobject obj,
                                                                                     jobjectArray languages) {
    auto opt = PointerFromCPointer<QWebEngineProfile>(env, obj);
    if (opt) {
        QWebEngineProfile* profile = opt.value();
        QStringList list {};
        for (int i = 0; i < env->GetArrayLength(languages); i++) {
            auto str = StringFromJstring(env, (jstring) env->GetObjectArrayElement(languages, i));
            list.append(QString::fromStdString(str));
        }
        SKryptonApp::runOnMainThreadBlocking([&]{
            profile->setSpellCheckLanguages(list);
        });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set spell check languages");
    }
}

