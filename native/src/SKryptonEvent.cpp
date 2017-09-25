#include <SKryptonEvent.h>

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonEvent_dispose_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QEvent>(env, obj);
    if (opt) delete opt.value();
}
