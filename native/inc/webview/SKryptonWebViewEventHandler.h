#ifndef SKRYPTONNATIVE_SKRYPTONWEBVIEWEVENTHANDLER_H
#define SKRYPTONNATIVE_SKRYPTONWEBVIEWEVENTHANDLER_H

#include <headers.h>
#include <jni_utils.h>

#include <QtCore>
#include <QWidget>

class SKryptonWebView;

class SKryptonWebViewEventHandler : public QWidget {
private:
    SKryptonWebView* webView;
    JNIEnv* env;
public:
    SKryptonWebViewEventHandler(SKryptonWebView* webView);
private:
    bool eventFilter(QObject* watched, QEvent* event);
    void mousePressEvent(QMouseEvent* event);
    void mouseReleaseEvent(QMouseEvent* event);
    void mouseDoubleClickEvent(QMouseEvent* event);
    void mouseMoveEvent(QMouseEvent* event);
    void mouseEvent(QMouseEvent* event);

    void keyPressEvent(QKeyEvent* event);
    void keyReleaseEvent(QKeyEvent* event);
    void keyEvent(QKeyEvent* event);
};

#endif //SKRYPTONNATIVE_SKRYPTONWEBVIEWEVENTHANDLER_H
