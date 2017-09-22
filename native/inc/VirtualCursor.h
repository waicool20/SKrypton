#ifndef SKRYPTONNATIVE_VIRTUALCURSOR_H
#define SKRYPTONNATIVE_VIRTUALCURSOR_H

#include <QtCore>
#include <QWidget>
#include <QPainter>

class VirtualCursor : public QWidget {
private:
    QImage* image;
public:
    VirtualCursor(QWidget* parent);
protected:
    void paintEvent(QPaintEvent* event) override;
};

#endif //SKRYPTONNATIVE_VIRTUALCURSOR_H
