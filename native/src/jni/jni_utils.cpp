#include <jni_utils.h>

JavaVM* gJvm = nullptr;
jobject gClassLoader = nullptr;
static jmethodID gFindClassMethod = nullptr;

//<editor-fold desc="Exception handler implementation">

bool CheckExceptions(JNIEnv* env) {
    return CheckExceptions(env, false);
}

bool CheckExceptions(JNIEnv* env, bool throwToJava) {
    return CheckExceptions(env, throwToJava, [](JNIEnv* l_env, jthrowable exception) {
        cerr << "Exception occurred during JNI Operation" << endl;
        l_env->ExceptionDescribe();
    });
}

bool CheckExceptions(JNIEnv* env, bool throwToJava, function<void(JNIEnv* env, jthrowable exception)> handler) {
    if (auto exception = env->ExceptionOccurred()) {
        env->ExceptionClear();
        handler(env, exception);
        if (throwToJava) env->Throw(exception);
        return true;
    }
    return false;
}

//</editor-fold>

//<editor-fold desc="Error throwing functions">

void ThrowNewError(JNIEnv* env, jclass clazz, string& message) {
    env->ThrowNew(clazz, message.c_str());
}

void ThrowNewError(string type, string message) {
    auto env = GetLocalJNIEnvRef();
    auto clazz = FindClass(type);
    if (clazz) {
        ThrowNewError(env, clazz.value(), message);
    } else {
        cerr << "Couldn't find exception type: " + type + " . Printing message here instead:"<< endl;
        cerr << message << endl;
    }
}

void ThrowNewError(JNIEnv* env, string type, string message) {
    replace(type.begin(), type.end(), '.', '/');
    auto clazz = env->FindClass(type.c_str());
    if (CheckExceptions(env, true)) return;
    ThrowNewError(env, clazz, message);
}

//</editor-fold>

JNIEnv* GetLocalJNIEnvRef() {
    JNIEnv* env = nullptr;
    if (gJvm->GetEnv((void**) &env, JNI_VERSION_1_8) == JNI_EDETACHED) {
        gJvm->AttachCurrentThreadAsDaemon((void**) env, nullptr);
    }
    return env;
}

optional<jclass> FindClass(string name) {
    auto env = GetLocalJNIEnvRef();
    auto classLoaderClass = env->GetObjectClass(gClassLoader);
    if (!gFindClassMethod) {
        gFindClassMethod = env->GetMethodID(classLoaderClass, "loadClass", "(Ljava/lang/String;)Ljava/lang/Class;");
        if (CheckExceptions(env)) {
            cerr << "Error getting method ID for method 'loadClass'" << endl;
            return {};
        }
    }

    auto obj = env->CallObjectMethod(gClassLoader, gFindClassMethod, JstringFromString(env, name));
    if (CheckExceptions(env)) {
        cerr << "Call to 'loadClass' failed" << endl;
        return {};
    }
    return { (jclass) obj };
}

