#include "SKyrptonApp.h"

static SKyrptonApp* app = nullptr;
static int argc = 0;
static vector<char*> argv {};

jlong Java_com_waicool20_skrypton_jni_objects_SKryptonApp_initialize_1N(JNIEnv* env, jobject obj, jobjectArray jargs) {
    argc = env->GetArrayLength(jargs);
    for (auto i = 0; i < argc; i++) {
        auto jstrObj = (jstring) env->GetObjectArrayElement(jargs, i);
        auto string = StringFromJstring(env, jstrObj);
        argv.push_back(string.data());
    }
    argv.push_back(nullptr);

    app = new SKyrptonApp(argc, argv.data());
    return (jlong) app;
}

jint Java_com_waicool20_skrypton_jni_objects_SKryptonApp_exec_1N(JNIEnv* env, jobject obj) {
    if (app == nullptr) {
        auto opt = PointerFromCPointer<SKyrptonApp>(env, obj);
        if (opt) {
            app = opt.value();
        } else return EXIT_FAILURE;
    }
    auto ret = app->exec();
    delete app;
    return ret;
}

void Java_com_waicool20_skrypton_jni_objects_SKryptonApp_dispose_1N(JNIEnv* env, jobject obj) {
    if (app == nullptr) {
        auto opt = PointerFromCPointer<SKyrptonApp>(env, obj);
        if (opt) {
            app = opt.value();
        } else return;
    }
    app->quit();
}

SKyrptonApp::SKyrptonApp(int &argc, char** argv) : QApplication(argc, argv) {}
