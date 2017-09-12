#include "SKyrptonApp.h"

static SKyrptonApp* app = nullptr;
static int argc = 0;
static vector<char*> argv {};

jlong Java_com_waicool20_skrypton_jni_objects_SKryptonApp_initialize_1N(JNIEnv* env, jobject obj, jobjectArray jargs) {
    argc = env->GetArrayLength(jargs);
    for (auto i = 0; i < argc; i++) {
        auto jstrObj = (jstring) env->GetObjectArrayElement(jargs, i);
        char* c = new char[env->GetStringLength(jstrObj)];
        auto charBuffer = env->GetStringUTFChars(jstrObj, 0);

        strcpy(c, charBuffer);
        argv.push_back(c);

        env->ReleaseStringUTFChars(jstrObj, charBuffer);
        env->DeleteLocalRef(jstrObj);
    }
    argv.push_back(nullptr);

    app = new SKyrptonApp(argc, argv.data());
    return (jlong) app;
}

jint Java_com_waicool20_skrypton_jni_objects_SKryptonApp_exec_1N(JNIEnv* env, jobject obj) {
    if (app == nullptr) { app = PointerFromCPointer<SKyrptonApp>(env, obj); }
    return app->exec();
}

void Java_com_waicool20_skrypton_jni_objects_SKryptonApp_destroy_1N(JNIEnv* env, jobject obj) {
    if (app == nullptr) { app = PointerFromCPointer<SKyrptonApp>(env, obj); }
    app->quit();
}

SKyrptonApp::SKyrptonApp(int &argc, char** argv) : QApplication(argc, argv) {}
