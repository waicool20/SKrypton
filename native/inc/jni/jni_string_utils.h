#ifndef SKRYPTONNATIVE_JNI_STRING_UTILS_H
#define SKRYPTONNATIVE_JNI_STRING_UTILS_H

#include <headers.h>
#include <jni.h>

string StringFromJstring(JNIEnv* env, jstring& jstr);
jstring JstringFromString(JNIEnv* env, string& str);

#endif //SKRYPTONNATIVE_JNI_STRING_UTILS_H
