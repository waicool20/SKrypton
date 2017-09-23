#include <SKryptonWebView.h>

SKryptonWebView::SKryptonWebView(jobject jInstance, const string& url) :
        jInstance(jInstance), eventHandler(new SKryptonWebViewEventHandler(this)) {
    auto container = new SKryptonWebViewContainer { this };
    setParent(container);
    cursor = new VirtualCursor { container };
    load(QUrl { url.c_str() });
}

void SKryptonWebView::loadStarted() {
    mIsLoading = true;
    installWebViewEventHandler(); // Needs to be re-installed every time a new page loads
    CallMethod<void*>(GetLocalJNIEnvRef(), jInstance, "loadStarted", "()V");
}

void SKryptonWebView::loadProgress(int progress) {
    CallMethod<void*>(GetLocalJNIEnvRef(), jInstance, "loadProgress", "(I)V", progress);
}

void SKryptonWebView::loadFinished(bool ok) {
    mIsLoading = false;
    getContainer()->setWindowTitle(title());
    CallMethod<void*>(GetLocalJNIEnvRef(), jInstance, "loadFinished", "(Z)V", ok);
}

bool SKryptonWebView::isLoading() {
    return mIsLoading;
}

jobject SKryptonWebView::getJInstance() {
    return jInstance;
}

SKryptonWebViewEventHandler* SKryptonWebView::getEventHandler() {
    return eventHandler;
}

void SKryptonWebView::installWebViewEventHandler() {
    for (auto child : children()) {
        if (QWidget* widget = dynamic_cast<QWidget*>(child)) {
            widget->installEventFilter(eventHandler);
            break;
        }
    }
}

VirtualCursor* SKryptonWebView::getVirtualCursor() {
    return cursor;
}

bool SKryptonWebView::isShowingCursor() {
    return showCursor;
}

void SKryptonWebView::setShowCursor(bool should) {
    showCursor = should;
    if (should) {
        cursor->show();
    } else {
        cursor->hide();
    }
}

SKryptonWebViewContainer* SKryptonWebView::getContainer() {
    return dynamic_cast<SKryptonWebViewContainer*>(parentWidget());
}
