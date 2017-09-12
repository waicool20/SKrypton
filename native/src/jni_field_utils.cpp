#include "jni_field_utils.h"

void CheckExceptions(JNIEnv* env) {
    //auto exceptionClass = env->FindClass("java/lang/Exception");
    if (env->ExceptionCheck()) {
        env->ExceptionDescribe();
        env->ExceptionClear();
        runtime_error("Exception occurred");
    }
}

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

jobject GetStaticObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    replace(type.begin(), type.end(), '.', '/');
    type.insert(0, "L");
    type.push_back(';');
    return env->GetStaticObjectField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), type.c_str()));
}

//</editor-fold>
