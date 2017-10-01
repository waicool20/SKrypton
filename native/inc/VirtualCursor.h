#ifndef SKRYPTONNATIVE_VIRTUALCURSOR_H
#define SKRYPTONNATIVE_VIRTUALCURSOR_H

#include <QtCore>
#include <QWidget>
#include <QPainter>

class VirtualCursor : public QWidget {
private:
    QImage* image = nullptr;
public:
    VirtualCursor(QWidget* parent);
    ~VirtualCursor();
protected:
    void paintEvent(QPaintEvent* event) override;
};

#endif //SKRYPTONNATIVE_VIRTUALCURSOR_H
