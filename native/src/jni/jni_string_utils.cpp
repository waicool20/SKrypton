#include <jni_string_utils.h>

string StringFromJstring(JNIEnv* env, jstring& jstr) {
    string str {};
    const char* chr = nullptr;
    if (jstr) chr = env->GetStringUTFChars(jstr, nullptr);
    if (chr) str.append(chr);
    if (jstr) env->ReleaseStringUTFChars(jstr, chr);
    return str;
}

jstring JstringFromString(JNIEnv* env, string& str) {
    char* data = new char[str.size()];
    strcpy(data, str.c_str());
    return env->NewStringUTF(data);
}

