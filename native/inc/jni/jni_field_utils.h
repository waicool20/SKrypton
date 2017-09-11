#ifndef SKRYPTONNATIVE_JNI_UTIL_H
#define SKRYPTONNATIVE_JNI_UTIL_H

#include <headers.h>
#include <jni.h>

void CheckObjNull(JNIEnv* env, const jobject &obj);

/* GetFieldValue helper template functions */

template<typename T>
T GetFieldValue(JNIEnv* env, jobject obj, string fieldName) = delete;

template<>
jboolean GetFieldValue<jboolean>(JNIEnv* env, jobject obj, string fieldName);

template<>
jbyte GetFieldValue<jbyte>(JNIEnv* env, jobject obj, string fieldName);

template<>
jchar GetFieldValue<jchar>(JNIEnv* env, jobject obj, string fieldName);

template<>
jshort GetFieldValue<jshort>(JNIEnv* env, jobject obj, string fieldName);

template<>
jint GetFieldValue<jint>(JNIEnv* env, jobject obj, string fieldName);

template<>
jlong GetFieldValue<jlong>(JNIEnv* env, jobject obj, string fieldName);

template<>
jfloat GetFieldValue<jfloat>(JNIEnv* env, jobject obj, string fieldName);

template<>
jdouble GetFieldValue<jdouble>(JNIEnv* env, jobject obj, string fieldName);

/* GetStaticFieldValue helper template functions */

template<typename T>
T GetStaticFieldValue(JNIEnv* env, jobject obj, string fieldName) = delete;

template<>
jboolean GetStaticFieldValue<jboolean>(JNIEnv* env, jobject obj, string fieldName);

template<>
jbyte GetStaticFieldValue<jbyte>(JNIEnv* env, jobject obj, string fieldName);

template<>
jchar GetStaticFieldValue<jchar>(JNIEnv* env, jobject obj, string fieldName);

template<>
jshort GetStaticFieldValue<jshort>(JNIEnv* env, jobject obj, string fieldName);

template<>
jint GetStaticFieldValue<jint>(JNIEnv* env, jobject obj, string fieldName);

template<>
jlong GetStaticFieldValue<jlong>(JNIEnv* env, jobject obj, string fieldName);

template<>
jfloat GetStaticFieldValue<jfloat>(JNIEnv* env, jobject obj, string fieldName);

template<>
jdouble GetStaticFieldValue<jdouble>(JNIEnv* env, jobject obj, string fieldName);

/* Get(Static)ObjectFieldValue helper functions */

jobject GetObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type);
jobject GetStaticObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type);


template<typename T>
T* PointerFromCPointer(JNIEnv* env, jobject obj) {
    auto CPointer = GetObjectFieldValue(env, obj, "handle", "com.waicool20.skrypton.CPointer");
    return (T*) GetFieldValue<jlong>(env, CPointer, "handle");
}

template<typename T>
T DeleteCPointerReference(JNIEnv* env, jobject obj) {
    delete PointerFromCPointer<T>(env, obj);
}

#endif //SKRYPTONNATIVE_JNI_UTIL_H
