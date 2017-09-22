#include <SKryptonWebViewContainer.h>
#include <SKryptonWebView.h>

SKryptonWebViewContainer::SKryptonWebViewContainer(SKryptonWebView* webView) : webView(webView) {}

SKryptonWebView* SKryptonWebViewContainer::getWebView() {
    return webView;
}

void SKryptonWebViewContainer::resizeEvent(QResizeEvent* event) {
    auto size = event->size();
    webView->setGeometry(0, 0, size.width(), size.height());
}

