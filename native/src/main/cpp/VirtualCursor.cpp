#include <VirtualCursor.h>

VirtualCursor::VirtualCursor(QWidget* parent) : QWidget { parent }, image(new QImage { ":images/cursor.png" }) {
    setAttribute(Qt::WA_NoSystemBackground);
    setAttribute(Qt::WA_TransparentForMouseEvents);
    setGeometry(0, 0, image->width(), image->height());
    hide();
}

VirtualCursor::~VirtualCursor() {
    delete image;
}

void VirtualCursor::paintEvent(QPaintEvent* event) {
    QPainter { this }.drawImage(QPointF { 0, 0 }, *image);
}
