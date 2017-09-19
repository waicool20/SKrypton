#include <SKryptonWebSettings.h>

void Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_resetAttribute_1N(JNIEnv* env, jobject obj, jint attr) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        settings->resetAttribute(static_cast<QWebEngineSettings::WebAttribute>(attr));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to reset attribute " + to_string(attr));
    }
}

void Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_setAttribute_1N(JNIEnv* env, jobject obj, jint attr, jboolean enabled) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        settings->setAttribute(static_cast<QWebEngineSettings::WebAttribute>(attr), enabled);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to set attribute " + to_string(attr));
    }
}

jboolean Java_com_waicool20_skrypton_jni_objects_SKryptonWebSettings_testAttribute_1N(JNIEnv* env, jobject obj, jint attr) {
    auto opt = PointerFromCPointer<QWebEngineSettings>(env, obj);
    if (opt) {
        QWebEngineSettings* settings = opt.value();
        return settings->testAttribute(static_cast<QWebEngineSettings::WebAttribute>(attr));
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to test attribute " + to_string(attr));
    }
}

