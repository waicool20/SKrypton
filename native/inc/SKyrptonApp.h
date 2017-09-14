#ifndef SKRYPTONNATIVE_SKYRPTONAPP_H
#define SKRYPTONNATIVE_SKYRPTONAPP_H

#include <jni_utils.h>

#include <QApplication>

#include <com_waicool20_skrypton_jni_objects_SKryptonApp.h>

Q_DECLARE_METATYPE(jobject)

class SKyrptonApp : public QApplication {
    Q_OBJECT
public:
    SKyrptonApp(int& argc, char** argv);
    using QApplication::exec;
    Q_INVOKABLE void runOnMainThread(jobject obj, jobject action);
};


#endif //SKRYPTONNATIVE_SKYRPTONAPP_H
