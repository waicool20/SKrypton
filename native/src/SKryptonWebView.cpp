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
        ThrowNewError(env, LOG_PREFIX + "Failed to load url " + url);
    }
}

void Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_setZoomFactor_1N(JNIEnv* env, jobject obj, jdouble factor) {
    auto opt = PointerFromCPointer<SKryptonWebView>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value();
        SKryptonApp::runOnMainThread([=] { view->setZoomFactor(factor); });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to set zoom factor to " + to_string(factor));
    }
}

jdouble Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_zoomFactor_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebView>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value();
        return view->zoomFactor();
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to get zoom factor");
    }
}

SKryptonWebView::SKryptonWebView(jobject jInstance, string& url) : jInstance(jInstance) {
    load(QUrl { url.c_str() });
}
