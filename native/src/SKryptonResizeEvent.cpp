#include <SKryptonResizeEvent.h>

JNIEXPORT jobject JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonResizeEvent_getNewSize_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QResizeEvent>(env, obj);
    if (opt) {
        QResizeEvent* event = opt.value();
        auto point = NewObject(env, "java.awt.Dimension", "(II)V", event->size().width(), event->size().height());
        if (point) return point.value();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get new size");
    return {};
}

JNIEXPORT jobject JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonResizeEvent_getOldSize_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QResizeEvent>(env, obj);
    if (opt) {
        QResizeEvent* event = opt.value();
        auto point = NewObject(env, "java.awt.Dimension", "(II)V", event->oldSize().width(), event->oldSize().height());
        if (point) return point.value();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get old size");
    return {};
}

