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
protected:
    bool eventFilter(QObject* watched, QEvent* event) override;
    void mousePressEvent(QMouseEvent* event) override;
    void mouseReleaseEvent(QMouseEvent* event) override;
    void mouseDoubleClickEvent(QMouseEvent* event) override;
    void mouseMoveEvent(QMouseEvent* event) override;
    void mouseEvent(QMouseEvent* event);
    void wheelEvent(QWheelEvent* event) override;

    void keyPressEvent(QKeyEvent* event) override;
    void keyReleaseEvent(QKeyEvent* event) override;
    void keyEvent(QKeyEvent* event);
};

#endif //SKRYPTONNATIVE_SKRYPTONWEBVIEWEVENTHANDLER_H
