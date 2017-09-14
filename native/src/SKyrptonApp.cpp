#include "SKyrptonApp.h"

static SKyrptonApp* app = nullptr;
static int argc = 0;
static vector<char*> argv {};

void Java_com_waicool20_skrypton_jni_objects_SKryptonApp_runOnMainThread_1N(JNIEnv* env, jobject obj, jobject action) {
    qRegisterMetaType<jobject>();
    auto g_obj = env->NewGlobalRef(obj);
    auto g_action = env->NewGlobalRef(action);
    QMetaObject::invokeMethod(app, "runOnMainThread", Qt::QueuedConnection, Q_ARG(jobject, g_obj),
                              Q_ARG(jobject, g_action));
}

jlong Java_com_waicool20_skrypton_jni_objects_SKryptonApp_initialize_1N(JNIEnv* env, jobject obj, jobjectArray jargs) {
    // Set up Global JVM References
    if (!gJvm) {
        if (env->GetJavaVM(&gJvm) < 0) {
            ThrowNewError(env, "java.lang.Exception", "Could not set Global JVM Ref");
            return {};
        }
        if (!gClassLoader) {
            auto classLoaderClass = env->FindClass("java/lang/ClassLoader");
            auto method = env->GetStaticMethodID(classLoaderClass, "getSystemClassLoader", "()Ljava/lang/ClassLoader;");
            if (CheckExceptions(env, true)) {
                cerr << "Error getting method ID for method 'getSystemClassLoader'" << endl;
                return {};
            }

            auto loader = env->CallStaticObjectMethod(classLoaderClass, method);
            if (CheckExceptions(env, true)) {
                cerr << "Error getting classloader reference" << endl;
                return {};
            }
            gClassLoader = env->NewGlobalRef(loader);
        }
    }
    // ----

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
        auto opt = PointerFromStaticCPointer<SKyrptonApp>(env, obj);
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
        auto opt = PointerFromStaticCPointer<SKyrptonApp>(env, obj);
        if (opt) {
            app = opt.value();
        } else return;
    }
    app->quit();
}

SKyrptonApp::SKyrptonApp(int& argc, char** argv) : QApplication(argc, argv) {}

void SKyrptonApp::runOnMainThread(jobject obj, jobject action) {
    JNIEnv* env = GetLocalJNIEnvRef();
    auto clazz = env->GetObjectClass(action);
    if (CheckExceptions(env, true)) {
        cerr << "Class" << endl;
        return;
    }
    auto method = env->GetMethodID(clazz, "run", "()V");
    if (CheckExceptions(env)) {
        cerr << "Method ID" << endl;
        return;
    }
    env->CallVoidMethod(action, method);
    env->DeleteGlobalRef(obj);
    env->DeleteGlobalRef(action);
}
