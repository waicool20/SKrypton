#include <SKryptonWebView.h>

static bool initialized = false;

jlong Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_initialize_1N(JNIEnv* env, jobject obj,
                                                                            jstring jurl) {
    if (initialized) {
        QtWebEngine::initialize();
        initialized = true;
    }
    auto url = StringFromJstring(env, jurl);
    auto ref = env->NewWeakGlobalRef(obj);
    return (jlong) new SKryptonWebView { ref, url };
}

void Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_load_1N(JNIEnv* env, jobject obj, jstring jurl) {
    auto opt = PointerFromCPointer<SKryptonWebView>(env, obj);
    auto url = StringFromJstring(env, jurl);
    if (opt) {
        SKryptonWebView* view = opt.value();
        SKryptonApp::runOnMainThread([=] { view->load(QUrl { url.c_str() }); });
    } else {
        ThrowNewError(env, "[SKryptonWebView] Failed to load url " + url);
    }
}

SKryptonWebView::SKryptonWebView(jobject jInstance, string& url) : jInstance(jInstance) {
    load(QUrl { url.c_str() });
}
