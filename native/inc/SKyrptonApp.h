#ifndef SKRYPTONNATIVE_SKYRPTONAPP_H
#define SKRYPTONNATIVE_SKYRPTONAPP_H

#include <com_waicool20_skrypton_jni_objects_SKryptonApp.h>
#include <jni_utils.h>
#include <QApplication>

class SKyrptonApp : public QApplication {
public:
    SKyrptonApp(int &argc, char** argv);
    using QApplication::exec;
};


#endif //SKRYPTONNATIVE_SKYRPTONAPP_H
