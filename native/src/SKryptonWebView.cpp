#include "SKryptonWebView.h"

bool initialized = false;

jlong Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_00024Factory_initialize_1N(JNIEnv* env, jobject obj,
                                                                                         jstring jurl) {
    if (initialized) {
        QtWebEngine::initialize();
        initialized = true;
    }
    auto url = StringFromJstring(env, jurl);
    return (jlong) new SKryptonWebView { url };
}

SKryptonWebView::SKryptonWebView(string& url) {
    load(QUrl { url.c_str() });
    show();
}
