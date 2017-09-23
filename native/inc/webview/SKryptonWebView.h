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
    VirtualCursor* cursor;
    bool showCursor = false;
    bool mIsLoading = true;
    jobject jInstance;
    SKryptonWebViewEventHandler* eventHandler;
public:
    SKryptonWebView(jobject jInstance, const string& url);
    ~SKryptonWebView();
    void loadStarted();
    void loadProgress(int progress);
    void loadFinished(bool ok);
    void installWebViewEventHandler();
    bool isLoading();
    jobject getJInstance();
    SKryptonWebViewEventHandler* getEventHandler();
    VirtualCursor* getVirtualCursor();
    bool isShowingCursor();
    void setShowCursor(bool should);
    SKryptonWebViewContainer* getContainer();
};

#endif //SKRYPTONNATIVE_SKRYPTONBROWSER_H
