#ifndef SKRYPTONNATIVE_JNI_STRING_UTILS_H
#define SKRYPTONNATIVE_JNI_STRING_UTILS_H

#include <headers.h>
#include <jni.h>

string StringFromJstring(JNIEnv* env, jstring& string);

#endif //SKRYPTONNATIVE_JNI_STRING_UTILS_H
