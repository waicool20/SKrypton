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

bool CheckExceptions(JNIEnv* env, bool throwToJava, function<void(JNIEnv* l_env, jthrowable exception)> handler) {
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

void ThrowNewError(JNIEnv* env, jclass clazz, const string& message) {
    env->ThrowNew(clazz, message.c_str());
}

void ThrowNewError(const string& type, const string& message) {
    auto env = GetLocalJNIEnvRef();
    auto clazz = FindClass(type);
    if (clazz) {
        ThrowNewError(env, clazz.value(), message);
    } else {
        cerr << LOG_PREFIX + "Couldn't find exception type: " + type << endl;
        cerr << LOG_PREFIX + "Printing message here instead:" << endl;
        cerr << LOG_PREFIX + message << endl;
    }
}

void ThrowNewError(JNIEnv* env, string type, const string& message) {
    replace(type.begin(), type.end(), '.', '/');
    auto clazz = env->FindClass(type.c_str());
    if (CheckExceptions(env, true)) return;
    ThrowNewError(env, clazz, message);
}

void ThrowNewError(JNIEnv* env, const string& message) {
    ThrowNewError(env, "java.lang.Exception", message);
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
            cerr << LOG_PREFIX + "Error getting method ID for method 'loadClass'" << endl;
            return {};
        }
    }

    auto obj = env->CallObjectMethod(gClassLoader, gFindClassMethod, JstringFromString(env, name));
    if (CheckExceptions(env)) {
        cerr << LOG_PREFIX + "Call to 'loadClass' on ClassLoader instance failed" << endl;
        cerr << LOG_PREFIX + "Failed to load class: " + name << endl;
        return {};
    }
    return { (jclass) obj };
}

static jmethodID getClassMethodID = nullptr;
static jmethodID classGetNameMethodID = nullptr;

optional<string> GetClassName(JNIEnv* env, jobject obj) {
    auto clazz = env->GetObjectClass(obj);
    if (!getClassMethodID) {
        getClassMethodID = env->GetMethodID(clazz, "getClass", "()Ljava/lang/Class;");
        if (CheckExceptions(env)) return {};
    }
    auto objClass = env->CallObjectMethod(obj, getClassMethodID);

    auto classClazz = env->GetObjectClass(objClass);

    if (!classGetNameMethodID) {
        classGetNameMethodID = env->GetMethodID(classClazz, "getName", "()Ljava/lang/String;");
        if (CheckExceptions(env)) return {};
    }
    auto jstr = (jstring) env->CallObjectMethod(objClass, classGetNameMethodID);
    return { StringFromJstring(env, jstr) };
}

optional<jobject> NewObject(JNIEnv* env, string clazz, string signature, ...) {
    auto jclazz = FindClass(clazz);
    if (jclazz) {
        auto constructor = env->GetMethodID(jclazz.value(), "<init>", signature.c_str());
        if (CheckExceptions(env)) return {};
        va_list args;
        va_start(args, signature);
        return { env->NewObjectV(jclazz.value(), constructor, args) };
    }
    return {};
}
