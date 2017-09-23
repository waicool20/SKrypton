#ifndef SKRYPTONNATIVE_HIGHLIGHTER_H
#define SKRYPTONNATIVE_HIGHLIGHTER_H

#include <headers.h>
#include <jni_utils.h>
#include <SKryptonApp.h>
#include <SKryptonWebViewContainer.h>

#include <QtCore>
#include <QWidget>
#include <QPainter>

#include <com_waicool20_skrypton_jni_objects_WebViewHighlighter_Companion.h>

class WebViewHighlighter : public QWidget {
private:
    QColor color;
    bool fillColor;
public:
    WebViewHighlighter(SKryptonWebViewContainer* parent, int x, int y, int width, int height, bool fillColor = false,
                       int red = 0, int green = 255, int blue = 0);
protected:
    void paintEvent(QPaintEvent* event) override;
};

#endif //SKRYPTONNATIVE_HIGHLIGHTER_H
