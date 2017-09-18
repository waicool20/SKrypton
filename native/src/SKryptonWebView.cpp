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
    auto app = new SKryptonWebView { ref, url };
    app->connect(app, &QWebEngineView::loadStarted, app, &SKryptonWebView::loadStarted);
    app->connect(app, &QWebEngineView::loadProgress, app, &SKryptonWebView::loadProgress);
    app->connect(app, &QWebEngineView::loadFinished, app, &SKryptonWebView::loadFinished);
    return (jlong) app;
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

void Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_back_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebView>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value();
        SKryptonApp::runOnMainThread([=] { view->back(); });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to go back");
    }
}

void Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_forward_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebView>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value();
        SKryptonApp::runOnMainThread([=] { view->back(); });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to go forward");
    }
}

void Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_reload_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebView>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value();
        SKryptonApp::runOnMainThread([=] { view->back(); });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to reload");
    }
}

void Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_stop_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebView>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value();
        SKryptonApp::runOnMainThread([=] { view->back(); });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to stop");
    }
}

void
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_setZoomFactor_1N(JNIEnv* env, jobject obj, jdouble factor) {
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

void SKryptonWebView::loadStarted() {
    CallMethod<void*>(GetLocalJNIEnvRef(), jInstance, "loadStarted", "()V");
}

void SKryptonWebView::loadProgress(int progress) {
    CallMethod<void*>(GetLocalJNIEnvRef(), jInstance, "loadProgress", "(I)V", progress);
}

void SKryptonWebView::loadFinished(bool ok) {
    CallMethod<void*>(GetLocalJNIEnvRef(), jInstance, "loadFinished", "(Z)V", ok);
}
