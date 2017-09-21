#ifndef SKRYPTONNATIVE_SKRYPTONBROWSER_H
#define SKRYPTONNATIVE_SKRYPTONBROWSER_H

#include <headers.h>
#include <jni_utils.h>

#include <SKryptonApp.h>

#include <QtCore>
#include <QtWebEngine>
#include <QWebEngineView>

#include <com_waicool20_skrypton_jni_objects_SKryptonWebView.h>

class SKryptonWebView;

class WebViewEventHandler : public QWidget{
private:
    SKryptonWebView* webView;
    JNIEnv* env;
public:
    WebViewEventHandler(SKryptonWebView* webView);
private:
    bool eventFilter(QObject* watched, QEvent* event);
    void mousePressEvent(QMouseEvent* event);
    void mouseReleaseEvent(QMouseEvent* event);
    void mouseDoubleClickEvent(QMouseEvent* event);
    void mouseMoveEvent(QMouseEvent* event);
    void mouseEvent(QMouseEvent* event);
};

class SKryptonWebView : public QWebEngineView {
Q_OBJECT
private:
    bool mIsLoading = true;
    jobject jInstance;
    WebViewEventHandler* webViewEventHandler;
public:
    SKryptonWebView(jobject jInstance, string& url);
    void loadStarted();
    void loadProgress(int progress);
    void loadFinished(bool ok);
    void installWebViewEventHandler();
    bool isLoading();
    jobject getJInstance();
    WebViewEventHandler* getWebViewEventHandler();
};


#endif //SKRYPTONNATIVE_SKRYPTONBROWSER_H
