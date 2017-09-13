#include <jni_string_utils.h>

string StringFromJstring(JNIEnv* env, jstring& jstr) {
    string str {};
    const char* chr = nullptr;
    if (jstr) chr = env->GetStringUTFChars(jstr, nullptr);
    if (chr) str.append(chr);
    if (jstr) env->ReleaseStringUTFChars(jstr, chr);
    return str;
}

