#ifndef SKRYPTONNATIVE_HIGHLIGHTER_H
#define SKRYPTONNATIVE_HIGHLIGHTER_H

#include <headers.h>
#include <SKryptonWebViewContainer.h>

#include <QtCore>
#include <QWidget>
#include <QPainter>

class WebViewHighlighter : public QWidget {
private:
    int x;
    int y;
    int width;
    int height;
    QColor color;
    bool fillColor;
public:
    WebViewHighlighter(SKryptonWebViewContainer* parent, int x, int y, int width, int height, bool fillColor = false,
                       int red = 0, int green = 255, int blue = 0, int alpha = 125);
protected:
    void paintEvent(QPaintEvent* event) override;
};

#endif //SKRYPTONNATIVE_HIGHLIGHTER_H
