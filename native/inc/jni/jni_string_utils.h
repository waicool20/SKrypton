#ifndef SKRYPTONNATIVE_JNI_STRING_UTILS_H
#define SKRYPTONNATIVE_JNI_STRING_UTILS_H

#include <jni_utils.h>

string StringFromJstring(JNIEnv* env, jstring jstr);
jstring JstringFromString(JNIEnv* env, const string& str);

#endif //SKRYPTONNATIVE_JNI_STRING_UTILS_H
