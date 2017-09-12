#include "jni_field_utils.h"

//<editor-fold desc="Exception handler implementation">

bool CheckExceptions(JNIEnv* env) {
    return CheckExceptions(env, false);
}

bool CheckExceptions(JNIEnv* env, bool throwToJava) {
    return CheckExceptions(env, throwToJava, [](JNIEnv* l_env, jthrowable exception) {
        cout << "Exception occurred during JNI Operation" << endl;
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

void CheckObjNull(JNIEnv* env, const jobject &obj) {
    if (!env) { throw std::runtime_error("JNIEnv is null"); }
    if (!obj) { throw std::runtime_error("jobject is null"); }
}

jfieldID GetFieldID(JNIEnv* env, jobject obj, string fieldName, string type) {
    auto clazz = env->GetObjectClass(obj);
    return env->GetFieldID(clazz, fieldName.c_str(), type.c_str());
}

//<editor-fold desc="Get(Static)ObjectFieldValue helper functions implementations">

jobject GetObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type) {
    CheckObjNull(env, obj);
    replace(type.begin(), type.end(), '.', '/');
    type.insert(0, "L");
    type.push_back(';');
    return env->GetObjectField(obj, GetFieldID(env, obj, fieldName.c_str(), type.c_str()));
}

optional<jobject> GetStaticObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    replace(type.begin(), type.end(), '.', '/');
    type.insert(0, "L");
    type.push_back(';');

    auto errorHandler = [&](JNIEnv* l_env, jthrowable exception) {
        cerr << "Error while trying to access field: " + fieldName + " with type: " + type << endl;
    };

    auto sField = env->GetStaticFieldID(clazz, fieldName.c_str(), type.c_str());
    if (CheckExceptions(env, false, errorHandler)) return {};

    auto value = (env->GetStaticObjectField(clazz, sField));
    if (CheckExceptions(env, false, errorHandler)) return {};
    return { value };
}

//</editor-fold>
