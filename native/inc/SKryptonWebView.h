#ifndef SKRYPTONNATIVE_SKRYPTONBROWSER_H
#define SKRYPTONNATIVE_SKRYPTONBROWSER_H

#include <headers.h>
#include <jni_utils.h>

#include <QtWebEngine>
#include <QWebEngineView>

#include <com_waicool20_skrypton_jni_objects_SKryptonWebView.h>
#include <com_waicool20_skrypton_jni_objects_SKryptonWebView_Factory.h>

class SKryptonWebView : QWebEngineView {
public:
    SKryptonWebView(string& url);
};


#endif //SKRYPTONNATIVE_SKRYPTONBROWSER_H
