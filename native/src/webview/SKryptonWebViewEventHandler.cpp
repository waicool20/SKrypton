#include <SKryptonWebViewEventHandler.h>
#include <SKryptonWebView.h>

SKryptonWebViewEventHandler::SKryptonWebViewEventHandler(SKryptonWebView* webView) {
    this->webView = webView;
    this->env = GetLocalJNIEnvRef();
}

bool SKryptonWebViewEventHandler::eventFilter(QObject* watched, QEvent* event) {
    this->event(event);
    return false;
}

//<editor-fold desc="Mouse Events">

void SKryptonWebViewEventHandler::mousePressEvent(QMouseEvent* event) {
    mouseEvent(event);
}

void SKryptonWebViewEventHandler::mouseReleaseEvent(QMouseEvent* event) {
    mouseEvent(event);
}

void SKryptonWebViewEventHandler::mouseDoubleClickEvent(QMouseEvent* event) {
    mouseEvent(event);
}

void SKryptonWebViewEventHandler::mouseMoveEvent(QMouseEvent* event) {
    webView->getVirtualCursor()->move(event->x(), event->y());
    if (webView->isShowingCursor()) {
        webView->getVirtualCursor()->show();
    } else {
        webView->getVirtualCursor()->hide();
    }
    mouseEvent(event);
}

void SKryptonWebViewEventHandler::mouseEvent(QMouseEvent* event) {
    auto pointerLong = (jlong) event;
    auto sMouseEvent = NewObject(env, "com.waicool20.skrypton.jni.objects.SKryptonMouseEvent", "(J)V", pointerLong);
    if (sMouseEvent) {
        CallMethod<void*>(env, webView->getJInstance(), "onMouseEvent",
                          "(ILcom/waicool20/skrypton/jni/objects/SKryptonMouseEvent;)V", event->type(),
                          sMouseEvent.value());
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not send mouse event to JVM instance");
    }
}

void SKryptonWebViewEventHandler::wheelEvent(QWheelEvent* event) {
    auto pointerLong = (jlong) event;
    auto sWheelEvent = NewObject(env, "com.waicool20.skrypton.jni.objects.SKryptonWheelEvent", "(J)V", pointerLong);
    if (sWheelEvent) {
        CallMethod<void*>(env, webView->getJInstance(), "onWheelEvent",
                          "(Lcom/waicool20/skrypton/jni/objects/SKryptonWheelEvent;)V", sWheelEvent.value());
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not send wheel event to JVM instance");
    }
}

//</editor-fold>

//<editor-fold desc="Key Events">

void SKryptonWebViewEventHandler::keyPressEvent(QKeyEvent* event) {
    keyEvent(event);
}

void SKryptonWebViewEventHandler::keyReleaseEvent(QKeyEvent* event) {
    keyEvent(event);
}

void SKryptonWebViewEventHandler::keyEvent(QKeyEvent* event) {
    auto pointerLong = (jlong) event;
    auto sMouseEvent = NewObject(env, "com.waicool20.skrypton.jni.objects.SKryptonKeyEvent", "(J)V",
                                 pointerLong);
    if (sMouseEvent) {
        CallMethod<void*>(env, webView->getJInstance(), "onKeyEvent",
                          "(ILcom/waicool20/skrypton/jni/objects/SKryptonKeyEvent;)V", event->type(),
                          sMouseEvent.value());
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not send key event to JVM instance");
    }
}

//</editor-fold>
