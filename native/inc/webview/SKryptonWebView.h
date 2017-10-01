#ifndef SKRYPTONNATIVE_SKRYPTONBROWSER_H
#define SKRYPTONNATIVE_SKRYPTONBROWSER_H

#include <headers.h>
#include <jni_utils.h>

#include <SKryptonApp.h>
#include <SKryptonWebViewEventHandler.h>
#include <SKryptonWebViewContainer.h>
#include <VirtualCursor.h>
#include <WebViewHighlighter.h>

#include <QtCore>
#include <QtWebEngine>
#include <QWebEngineView>

#include <com_waicool20_skrypton_jni_objects_SKryptonWebView.h>

class SKryptonWebView : public QWebEngineView {
Q_OBJECT
private:
    VirtualCursor* virtualCursor = nullptr;
    bool showCursor = false;
    bool mIsLoading = true;
    jobject jInstance = nullptr;
    SKryptonWebViewEventHandler* eventHandler = nullptr;
public:
    SKryptonWebView(jobject jInstance, const string& url);
    ~SKryptonWebView();
    void loadStarted();
    void loadProgress(int progress);
    void loadFinished(bool ok);
    void installWebViewEventHandler();
    bool isLoading() const;
    jobject getJInstance() const;
    SKryptonWebViewEventHandler* getEventHandler() const;
    VirtualCursor* getVirtualCursor() const;
    bool isShowingCursor() const;
    void setShowCursor(bool should);
    SKryptonWebViewContainer* getContainer() const;
};

#endif //SKRYPTONNATIVE_SKRYPTONBROWSER_H
