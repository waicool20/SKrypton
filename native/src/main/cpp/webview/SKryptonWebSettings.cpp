#include <SKryptonWebSettings.h>

JNIEXPORT jint JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_getFontSize_1N(JNIEnv* env, jobject obj, jint font) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        return settings->fontSize(static_cast<QWebEngineSettings::FontSize>(font));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not get font size");
        return {};
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_setFontSize_1N(JNIEnv* env, jobject obj, jint font,
                                                                           jint size) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        settings->setFontSize(static_cast<QWebEngineSettings::FontSize>(font), size);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set font size");
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_resetFontSize_1N(JNIEnv* env, jobject obj, jint font) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        settings->resetFontSize(static_cast<QWebEngineSettings::FontSize>(font));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not reset font size");
    }
}

JNIEXPORT jstring JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_getFontFamily_1N(JNIEnv* env, jobject obj, jint family) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        auto sFamily = settings->fontFamily(static_cast<QWebEngineSettings::FontFamily>(family)).toStdString();
        return JstringFromString(env, sFamily);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not get font family");
        return {};
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_setFontFamily_1N(JNIEnv* env, jobject obj, jint whichFamily,
                                                                             jstring family) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        auto qFamily = QString::fromStdString(StringFromJstring(env, family));
        settings->setFontFamily(static_cast<QWebEngineSettings::FontFamily>(whichFamily), qFamily);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set font family");
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_resetFontFamily_1N(JNIEnv* env, jobject obj, jint family) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        settings->resetFontFamily(static_cast<QWebEngineSettings::FontFamily>(family));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not reset font ");
    }
}

JNIEXPORT jobject JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_00024Companion_defaultSettings_1N(JNIEnv* env,
                                                                                              jobject obj) {
    auto settingsPointer = (jlong) QWebEngineSettings::defaultSettings();
    auto jSettings = NewObject(env, "com.waicool20.skrypton.jni.objects.SKryptonWebSettings", "(J)V", settingsPointer);
    if (jSettings) return jSettings.value();
    ThrowNewError(env, LOG_PREFIX + "Could not retrieve default webview settings");
    return {};
}

JNIEXPORT jstring JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_getDefaultTextEncoding_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        auto charset = settings->defaultTextEncoding().toStdString();
        return JstringFromString(env, charset);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not retrieve default charset");
        return {};
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_setDefaultTextEncoding_1N(JNIEnv* env, jobject obj,
                                                                                      jstring charset) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        settings->setDefaultTextEncoding(QString::fromStdString(StringFromJstring(env, charset)));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set default charset");
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_resetAttribute_1N(JNIEnv* env, jobject obj, jint attr) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        settings->resetAttribute(static_cast<QWebEngineSettings::WebAttribute>(attr));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to reset attribute " + to_string(attr));
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_setAttribute_1N(JNIEnv* env, jobject obj, jint attr,
                                                                            jboolean enabled) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        settings->setAttribute(static_cast<QWebEngineSettings::WebAttribute>(attr), enabled);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to set attribute " + to_string(attr));
    }
}

JNIEXPORT jboolean JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_testAttribute_1N(JNIEnv* env, jobject obj, jint attr) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        return settings->testAttribute(static_cast<QWebEngineSettings::WebAttribute>(attr));
    }
        ThrowNewError(env, LOG_PREFIX + "Failed to test attribute " + to_string(attr));
        return {};

}

