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
        auto path = profile->cachePath().toStdString();
        return JstringFromString(env, path);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not get cache path");
        return {};
    }
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

