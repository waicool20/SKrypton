#include "SKryptonApp.h"

static SKryptonApp* app = nullptr;
static int argc = 0;
static vector<char*> argv {};

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonApp_putEnv_1N(JNIEnv* env, jobject obj, jstring key, jstring value) {
    qputenv(StringFromJstring(env, key).c_str(), StringFromJstring(env, value).c_str());
}

JNIEXPORT jstring JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonApp_getEnv_1N(JNIEnv* env, jobject obj, jstring key) {
    auto value = qgetenv(StringFromJstring(env, key).c_str()).toStdString();
    return JstringFromString(env, value);
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonApp_runOnMainThread_1N(JNIEnv* env, jobject obj, jobject action) {
    qRegisterMetaType<jobject>();
    auto g_obj = env->NewGlobalRef(obj);
    auto g_action = env->NewGlobalRef(action);
    QMetaObject::invokeMethod(app, "runOnMainThread", Qt::QueuedConnection, Q_ARG(jobject, g_obj),
                              Q_ARG(jobject, g_action));
}

JNIEXPORT jlong JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonApp_initialize_1N(JNIEnv* env, jobject obj, jobjectArray jargs) {
    // Set up Global JVM References
    if (!gJvm) {
        if (env->GetJavaVM(&gJvm) < 0) {
            ThrowNewError(env, "Could not set Global JVM Ref");
            return {};
        }
        if (!gClassLoader) {
            auto loader = CallStaticMethod<jobject>(env, "java/lang/ClassLoader", "getSystemClassLoader",
                                                    "()Ljava/lang/ClassLoader;");

            if (!loader) {
                ThrowNewError(env, "Error getting classloader reference");
                return {};
            }
            gClassLoader = env->NewGlobalRef(loader.value());
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

    QApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
    app = new SKryptonApp(argc, argv.data());
    return (jlong) app;
}

JNIEXPORT jint JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonApp_exec_1N(JNIEnv* env, jobject obj) {
    if (app == nullptr) {
        auto opt = PointerFromStaticCPointer<SKryptonApp>(env, obj);
        if (opt) {
            app = opt.value();
        } else return EXIT_FAILURE;
    }
    auto ret = app->exec();
    delete app;
    return ret;
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonApp_dispose_1N(JNIEnv* env, jobject obj) {
    if (app == nullptr) {
        auto opt = PointerFromStaticCPointer<SKryptonApp>(env, obj);
        if (opt) {
            app = opt.value();
        } else return;
    }
    app->quit();
}

SKryptonApp::SKryptonApp(int& argc, char** argv) : QApplication(argc, argv) {}

void SKryptonApp::runOnMainThread(jobject obj, jobject action) {
    JNIEnv* env = GetLocalJNIEnvRef();
    CallMethod<void*>(env, action, "run", "()V");
    env->DeleteGlobalRef(obj);
    env->DeleteGlobalRef(action);
}

void SKryptonApp::runOnMainThreadAsync(const function<void()>& action) {
    QObject s;
    QObject::connect(&s, &QObject::destroyed, qApp, action);
}

void SKryptonApp::runOnMainThread(const function<void()>& action) {
    std::mutex mutex;
    std::condition_variable cv;

    auto isDone = false;
    runOnMainThreadAsync([&] {
        action();
        isDone = true;
        cv.notify_one();
    });

    std::unique_lock<std::mutex> lock(mutex);
    cv.wait(lock, [&] { return isDone; });
}

