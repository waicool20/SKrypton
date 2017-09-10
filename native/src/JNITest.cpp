#include <com_waicool20_skrypton_jni_JNITest.h>
#include <bits/stdc++.h>

using namespace std;

JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_JNITest_say(JNIEnv *env, jobject obj, jstring str){
    cout << (*env).GetStringUTFChars(str, NULL) << endl;
}
