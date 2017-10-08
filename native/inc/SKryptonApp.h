#ifndef SKRYPTONNATIVE_SKYRPTONAPP_H
#define SKRYPTONNATIVE_SKYRPTONAPP_H

#include <headers.h>
#include <jni_utils.h>

#include <QApplication>
#include <QThread>

#include <com_waicool20_skrypton_jni_objects_SKryptonApp.h>

Q_DECLARE_METATYPE(jobject)

class SKryptonApp : public QApplication {
    Q_OBJECT
public:
    SKryptonApp(int& argc, char** argv);
    static void runOnMainThread(const function<void()> &action);
    static void runOnMainThreadBlocking(const function<void()> &action);

    using QApplication::exec;
    Q_INVOKABLE void runOnMainThread(jobject obj, jobject action);
};

#endif //SKRYPTONNATIVE_SKYRPTONAPP_H
