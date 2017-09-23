#include <WebViewHighlighter.h>

WebViewHighlighter::WebViewHighlighter(SKryptonWebViewContainer* parent, int x, int y, int width, int height,
                                       bool fillColor, int red, int green, int blue, int alpha) :
        QWidget { parent }, x(x), y(y), width(width), height(height), color(QColor { red, green, blue, alpha }),
        fillColor(fillColor) {
    setAttribute(Qt::WA_NoSystemBackground);
    setAttribute(Qt::WA_TransparentForMouseEvents);
    setGeometry(x, y, width, height);
}

void WebViewHighlighter::paintEvent(QPaintEvent* event) {
    QPainter painter { this };
    QPen pen { color, 5 };
    painter.setPen(pen);
    painter.drawRect(0, 0, width, height);
    if (fillColor) painter.fillRect(0, 0, width, height, color.lighter(150));
}

