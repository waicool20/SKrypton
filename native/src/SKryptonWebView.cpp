#include <SKryptonWebView.h>

static bool initialized = false;

WebViewEventHandler::WebViewEventHandler(SKryptonWebView* webView) {
    this->webView = webView;
    this->env = GetLocalJNIEnvRef();
}

bool WebViewEventHandler::eventFilter(QObject* watched, QEvent* event) {
    this->event(event);
    return false;
}

//<editor-fold desc="Mouse Events">

void WebViewEventHandler::mousePressEvent(QMouseEvent* event) {
    mouseEvent(event);
}

void WebViewEventHandler::mouseReleaseEvent(QMouseEvent* event) {
    mouseEvent(event);
}

void WebViewEventHandler::mouseDoubleClickEvent(QMouseEvent* event) {
    mouseEvent(event);
}

void WebViewEventHandler::mouseMoveEvent(QMouseEvent* event) {
    mouseEvent(event);
}

void WebViewEventHandler::mouseEvent(QMouseEvent* event) {
    auto pointerLong = (jlong) event;
    auto sMouseEvent = NewObject(env, "com.waicool20.skrypton.jni.objects.SKryptonMouseEvent", "(J)V",
                                 pointerLong);
    if (sMouseEvent) {
        CallMethod<void*>(env, webView->jInstance, "onMouseEvent",
                          "(ILcom/waicool20/skrypton/jni/objects/SKryptonMouseEvent;)V", event->type(),
                          sMouseEvent.value());
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not send mouse event to JVM instance");
    }
}

//</editor-fold>

jlong Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_initialize_1N(JNIEnv* env, jobject obj,
                                                                            jstring jurl) {
    if (initialized) {
        QtWebEngine::initialize();
        initialized = true;
    }
    auto url = StringFromJstring(env, jurl);
    auto ref = env->NewWeakGlobalRef(obj);
    auto view = new SKryptonWebView { ref, url };
    view->connect(view, &QWebEngineView::loadStarted, view, &SKryptonWebView::loadStarted);
    view->connect(view, &QWebEngineView::loadProgress, view, &SKryptonWebView::loadProgress);
    view->connect(view, &QWebEngineView::loadFinished, view, &SKryptonWebView::loadFinished);
    return (jlong) view;
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

jobject Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_getSettings_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebView>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value();
        auto settingsPointer = (jlong) view->settings();
        auto jSettings = NewObject(env, "com.waicool20.skrypton.jni.objects.SKryptonWebSettings", "(J)V",
                                   settingsPointer);
        if (jSettings) return jSettings.value();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to retrieve settings");
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

jbyteArray JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_takeScreenshot_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebView>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value();
        if (view->isHidden()) {
            ThrowNewError(env, LOG_PREFIX + "Window must be showing to take screenshot");
            return {};
        }
        QPixmap pixmap { view->size() };
        auto isDone = false;
        SKryptonApp::runOnMainThread([&pixmap, &isDone, view] {
            view->render(&pixmap, QPoint(), QRegion(view->rect()));
            isDone = true;
        });
        while (!isDone) {}
        QByteArray byteArray;
        QBuffer buffer(&byteArray);
        pixmap.save(&buffer, "PNG");
        auto arr = env->NewByteArray(byteArray.size());
        env->SetByteArrayRegion(arr, 0, byteArray.size() - 1, (jbyte*) byteArray.data());
        return arr;
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to take screenshot");
    }
}

SKryptonWebView::SKryptonWebView(jobject jInstance, string& url) :
        jInstance(jInstance), webViewEventHandler(new WebViewEventHandler(this)) {
    load(QUrl { url.c_str() });
}

void SKryptonWebView::loadStarted() {
    installWebViewEventHandler(); // Needs to be re-installed every time a new page loads
    CallMethod<void*>(GetLocalJNIEnvRef(), jInstance, "loadStarted", "()V");
}

void SKryptonWebView::loadProgress(int progress) {
    CallMethod<void*>(GetLocalJNIEnvRef(), jInstance, "loadProgress", "(I)V", progress);
}

void SKryptonWebView::loadFinished(bool ok) {
    CallMethod<void*>(GetLocalJNIEnvRef(), jInstance, "loadFinished", "(Z)V", ok);
}

void SKryptonWebView::installWebViewEventHandler() {
    for (auto child : children()) {
        if (QWidget* widget = dynamic_cast<QWidget*>(child)) {
            widget->installEventFilter(webViewEventHandler);
            break;
        }
    }
}
