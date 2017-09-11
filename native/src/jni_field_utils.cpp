#include "jni_field_utils.h"

void CheckObjNull(JNIEnv* env, const jobject &obj) {
    if (!env) { throw std::runtime_error("JNIEnv is null"); }
    if (!obj) { throw std::runtime_error("jobject is null"); }
}

/* GetFieldValue helper template functions implementations */

template<>
jboolean GetFieldValue<jboolean>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetBooleanField(obj, env->GetFieldID(clazz, fieldName.c_str(), "Z"));
}

template<>
jbyte GetFieldValue<jbyte>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetByteField(obj, env->GetFieldID(clazz, fieldName.c_str(), "B"));
}

template<>
jchar GetFieldValue<jchar>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetCharField(obj, env->GetFieldID(clazz, fieldName.c_str(), "C"));
}

template<>
jshort GetFieldValue<jshort>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetShortField(obj, env->GetFieldID(clazz, fieldName.c_str(), "S"));
}

template<>
jint GetFieldValue<jint>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetIntField(obj, env->GetFieldID(clazz, fieldName.c_str(), "I"));
}

template<>
jlong GetFieldValue<jlong>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetLongField(obj, env->GetFieldID(clazz, fieldName.c_str(), "J"));
}

template<>
jfloat GetFieldValue<jfloat>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetFloatField(obj, env->GetFieldID(clazz, fieldName.c_str(), "F"));
}

template<>
jdouble GetFieldValue<jdouble>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetDoubleField(obj, env->GetFieldID(clazz, fieldName.c_str(), "D"));
}

/* GetStaticFieldValue helper template functions implementations */

template<>
jboolean GetStaticFieldValue<jboolean>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticBooleanField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "Z"));
}

template<>
jbyte GetStaticFieldValue<jbyte>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticByteField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "B"));
}

template<>
jchar GetStaticFieldValue<jchar>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticCharField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "C"));
}

template<>
jshort GetStaticFieldValue<jshort>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticShortField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "S"));
}

template<>
jint GetStaticFieldValue<jint>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticIntField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "I"));
}

template<>
jlong GetStaticFieldValue<jlong>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticLongField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "J"));
}

template<>
jfloat GetStaticFieldValue<jfloat>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticFloatField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "F"));
}

template<>
jdouble GetStaticFieldValue<jdouble>(JNIEnv* env, jobject obj, string fieldName) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    return env->GetStaticDoubleField(clazz, env->GetStaticFieldID(clazz, fieldName.c_str(), "D"));
}

/* Get(Static)ObjectFieldValue helper functions implementations */

jobject GetObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    replace(type.begin(), type.end(), '.', '/');
    type.insert(0, "L");
    return env->GetObjectField(obj, env->GetFieldID(clazz, fieldName.c_str(), type.c_str()));
}

jobject GetStaticObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type) {
    CheckObjNull(env, obj);
    jclass clazz = env->GetObjectClass(obj);
    replace(type.begin(), type.end(), '.', '/');
    type.insert(0, "L");
    return env->GetStaticObjectField(clazz, env->GetFieldID(clazz, fieldName.c_str(), type.c_str()));
}

/* */
