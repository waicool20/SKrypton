#include <SKryptonWebView.h>

static bool initialized = false;

JNIEXPORT jlong JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_initialize_1N(JNIEnv* env, jobject obj,
                                                                      jstring jurl) {
    if (!initialized) {
        QtWebEngine::initialize();
        initialized = true;
    }
    auto url = StringFromJstring(env, jurl);
    auto ref = env->NewWeakGlobalRef(obj);
    auto view = new SKryptonWebView { ref, url };
    view->connect(view, &QWebEngineView::loadStarted, view, &SKryptonWebView::loadStarted);
    view->connect(view, &QWebEngineView::loadProgress, view, &SKryptonWebView::loadProgress);
    view->connect(view, &QWebEngineView::loadFinished, view, &SKryptonWebView::loadFinished);
    return (jlong) view->getContainer();
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_load_1N(JNIEnv* env, jobject obj, jstring jurl) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    auto url = StringFromJstring(env, jurl);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        SKryptonApp::runOnMainThread([=] { view->load(QUrl { url.c_str() }); });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to load url " + url);
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_back_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        SKryptonApp::runOnMainThread([=] { view->back(); });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to go back");
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_forward_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        SKryptonApp::runOnMainThread([=] { view->back(); });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to go forward");
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_reload_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        SKryptonApp::runOnMainThread([=] { view->back(); });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to reload");
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_stop_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        SKryptonApp::runOnMainThread([=] { view->back(); });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to stop");
    }
}

JNIEXPORT jobject JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_getSettings_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        auto settingsPointer = (jlong) view->settings();
        auto jSettings = NewObject(env, "com.waicool20.skrypton.jni.objects.SKryptonWebSettings", "(J)V",
                                   settingsPointer);
        if (jSettings) return jSettings.value();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to retrieve settings");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_setZoomFactor_1N(JNIEnv* env, jobject obj, jdouble factor) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        SKryptonApp::runOnMainThread([=] { view->setZoomFactor(factor); });
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to set zoom factor to " + to_string(factor));

}

JNIEXPORT jdouble JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_zoomFactor_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        return view->zoomFactor();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to get zoom factor");
    return {};
}

JNIEXPORT jbyteArray JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_takeScreenshot_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        if (view->isHidden()) {
            ThrowNewError(env, LOG_PREFIX + "Window must be showing to take screenshot");
            return {};
        }
        QPixmap pixmap { view->size() };
        auto isDone = std::make_shared<bool>(new bool(false));
        SKryptonApp::runOnMainThread([&pixmap, isDone, view] {
            view->render(&pixmap, QPoint(), QRegion(view->rect()));
            *isDone = true;
        });
        while (!*isDone) {}
        QByteArray byteArray;
        QBuffer buffer(&byteArray);
        pixmap.save(&buffer, "PNG");
        auto arr = env->NewByteArray(byteArray.size());
        env->SetByteArrayRegion(arr, 0, byteArray.size() - 1, (jbyte*) byteArray.data());
        return arr;
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to take screenshot");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_sendEvent_1N(JNIEnv* env, jobject obj, jobject jEvent) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    auto opt2 = PointerFromCPointer<QEvent>(env, jEvent);
    if (opt && opt2) {
        SKryptonWebView* view = opt.value()->getWebView();
        QEvent* event = opt2.value();
        for (auto child : view->children()) {
            if (QWidget* widget = dynamic_cast<QWidget*>(child)) {
                QApplication::postEvent(widget, event);
                break;
            }
        }
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to pass event");
    }
}

JNIEXPORT jboolean JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_isLoading_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        return view->isLoading();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to check if loading");
    return {};
}

JNIEXPORT jstring JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_url_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        auto url = view->url().toString().toStdString();
        return JstringFromString(env, url);
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to check if loading");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_runJavaScript_1N(JNIEnv* env, jobject obj, jstring content,
                                                                         jobject callback) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        auto qContent = QString::fromStdString(StringFromJstring(env, content));
        auto gCallback = env->NewGlobalRef(callback);
        view->page()->runJavaScript(qContent, [gCallback](const QVariant& v) {
            auto localEnv = GetLocalJNIEnvRef();
            CallMethod<void*>(localEnv, gCallback, "run", "()V");
            localEnv->DeleteGlobalRef(gCallback);
        });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to execute Javascript");
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_loadHtml_1N(JNIEnv* env, jobject obj, jstring content,
                                                                    jstring baseUrl) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        auto qContent = QString::fromStdString(StringFromJstring(env, content));
        auto qBaseUrl = QString::fromStdString(StringFromJstring(env, baseUrl));
        SKryptonApp::runOnMainThread([=] {
            view->setHtml(qContent, qBaseUrl);
        });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to load html content");
    }
}

JNIEXPORT jboolean JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_isShowingCursor_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        return view->isShowingCursor();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to check if we should show cursor");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_setShowingCursor_1N(JNIEnv* env, jobject obj, jboolean should) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        view->setShowCursor(should);
    } else {
        ThrowNewError(env, LOG_PREFIX + "Failed to set show cursor value");
    }
}

JNIEXPORT jint JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_getCursorX_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        return view->getVirtualCursor()->x();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to set get cursorX");
    return {};
}

JNIEXPORT jint JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_getCursorY_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<SKryptonWebViewContainer>(env, obj);
    if (opt) {
        SKryptonWebView* view = opt.value()->getWebView();
        return view->getVirtualCursor()->y();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to get cursorY");
    return {};
}
