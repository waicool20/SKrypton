#ifndef SKRYPTONNATIVE_JNI_UTILS_H_H
#define SKRYPTONNATIVE_JNI_UTILS_H_H

#include <jni.h>
#include <jni_field_utils.h>
#include <jni_pointer_utils.h>
#include <jni_string_utils.h>

#define LOG_PREFIX string { "[" } + __FUNCTION__ + "] "

extern JavaVM* gJvm;
extern jobject gClassLoader;

//<editor-fold desc="Exception handlers">

bool CheckExceptions(JNIEnv* env);
bool CheckExceptions(JNIEnv* env, bool throwToJava);
bool CheckExceptions(JNIEnv* env, bool throwToJava, function<void(JNIEnv* env, jthrowable exception)> handler);

//</editor-fold>

//<editor-fold desc="Error throwing functions">

void ThrowNewError(const string& type, const string& message);
void ThrowNewError(JNIEnv* env, string type, const string& message);
void ThrowNewError(JNIEnv* env, const string& message);

//</editor-fold>

JNIEnv* GetLocalJNIEnvRef();
optional<jclass> FindClass(string name);
optional<string> GetClassName(JNIEnv* env, jobject obj);

#endif //SKRYPTONNATIVE_JNI_UTILS_H_H
