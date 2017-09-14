#ifndef SKRYPTONNATIVE_JNI_UTILS_H_H
#define SKRYPTONNATIVE_JNI_UTILS_H_H

#include <jni.h>
#include <jni_field_utils.h>
#include <jni_pointer_utils.h>
#include <jni_string_utils.h>

extern JavaVM* gJvm;
extern jobject gClassLoader;

//<editor-fold desc="Exception handlers">

bool CheckExceptions(JNIEnv* env);
bool CheckExceptions(JNIEnv* env, bool throwToJava);
bool CheckExceptions(JNIEnv* env, bool throwToJava, function<void(JNIEnv* env, jthrowable exception)> handler);

//</editor-fold>

JNIEnv* GetLocalJNIEnvRef();
void ThrowNewError(string type, string message);
void ThrowNewError(JNIEnv* env, string type, string message);
optional<jclass> FindClass(string name);

#endif //SKRYPTONNATIVE_JNI_UTILS_H_H
