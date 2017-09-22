#ifndef SKRYPTONNATIVE_SKRYPTONWEBVIEWCONTAINER_H
#define SKRYPTONNATIVE_SKRYPTONWEBVIEWCONTAINER_H

#include <headers.h>

#include <QtCore>
#include <QWidget>

class SKryptonWebView;

class SKryptonWebViewContainer : public QWidget {
private:
    SKryptonWebView* webView;
public:
    SKryptonWebViewContainer(SKryptonWebView* webView);
    SKryptonWebView* getWebView();
protected:
    void resizeEvent(QResizeEvent* event) override;
};

#endif //SKRYPTONNATIVE_SKRYPTONWEBVIEWCONTAINER_H
