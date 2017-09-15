#ifndef SKRYPTONNATIVE_SKRYPTONBROWSER_H
#define SKRYPTONNATIVE_SKRYPTONBROWSER_H

#include <headers.h>
#include <jni_utils.h>

#include <SKryptonApp.h>

#include <QtWebEngine>
#include <QWebEngineView>

#include <com_waicool20_skrypton_jni_objects_SKryptonWebView.h>

class SKryptonWebView : public QWebEngineView {
    Q_OBJECT
public:
    SKryptonWebView(jobject jInstance, string& url);
private:
    jobject jInstance;
};


#endif //SKRYPTONNATIVE_SKRYPTONBROWSER_H
