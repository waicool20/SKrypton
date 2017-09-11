#include "SKyrptonApp.h"

SKyrptonApp* app = nullptr;

jlong Java_com_waicool20_skrypton_jni_objects_SKryptonApp_initialize_1N(JNIEnv* env, jobject obj, jobjectArray jargs) {
    auto size = env->GetArrayLength(jargs);
    char** args;
    for (auto i = 0; i < size; i++) {
        auto jstr = (jstring) env->GetObjectArrayElement(jargs, i);
        args[i] = (char*) env->GetStringChars(jstr, nullptr);
    }
    app = new SKyrptonApp(size, args);
    return reinterpret_cast<jlong>(app);
}

jint Java_com_waicool20_skrypton_jni_objects_SKryptonApp_exec_1N(JNIEnv* env, jobject obj) {
    if (!app) { app = (SKyrptonApp*) GetStaticFieldValue<jlong>(env, obj, "handle"); }
    return app->exec();
}

void Java_com_waicool20_skrypton_jni_objects_SKryptonApp_destroy_1N(JNIEnv* env, jobject obj) {
    if (!app) { app = PointerFromCPointer<SKyrptonApp>(env, obj); }
    app->quit();
}

SKyrptonApp::SKyrptonApp(int &argc, char** argv) : QApplication(argc, argv) {}
