#include <SKryptonWheelEvent.h>

JNIEXPORT jlong JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWheelEvent_00024Companion_initialize_1N(JNIEnv* env, jobject obj,
                                                                                        jint localPosX, jint localPosY,
                                                                                        jint delta, jlong e_buttons,
                                                                                        jlong e_modifiers,
                                                                                        jint e_orientation) {
    auto localPos = QPointF { (double) localPosX, (double) localPosY };
    auto buttons = static_cast<Qt::MouseButton>(e_buttons);
    auto modifiers = static_cast<Qt::KeyboardModifier>(e_modifiers);
    auto orientation = static_cast<Qt::Orientation>(e_orientation);
    return (jlong) new QWheelEvent(localPos, delta, buttons, modifiers, orientation);
}

JNIEXPORT jboolean JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWheelEvent_isInverted_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWheelEvent>(env, obj);
    if (ref) {
        QWheelEvent* event = ref.value();
        return event->inverted();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to check if inverted");
    return {};
}

JNIEXPORT jint JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWheelEvent_getPhase_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWheelEvent>(env, obj);
    if (ref) {
        QWheelEvent* event = ref.value();
        return event->phase();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to get phase");
    return {};
}

JNIEXPORT jint JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWheelEvent_getDelta_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWheelEvent>(env, obj);
    if (ref) {
        QWheelEvent* event = ref.value();
        return event->delta();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to get delta");
    return {};
}

JNIEXPORT jobject JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWheelEvent_getLocalPos_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWheelEvent>(env, obj);
    if (ref) {
        QWheelEvent* event = ref.value();
        auto point = NewObject(env, "java.awt.Point", "(II)V", event->x(), event->y());
        if (point) return point.value();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to get local position");
    return {};
}

JNIEXPORT jobject JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWheelEvent_getGlobalPos_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWheelEvent>(env, obj);
    if (ref) {
        QWheelEvent* event = ref.value();
        auto point = NewObject(env, "java.awt.Point", "(II)V", event->globalX(), event->globalY());
        if (point) return point.value();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to get global position");
    return {};
}

JNIEXPORT jlong JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWheelEvent_getButtons_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWheelEvent>(env, obj);
    if (ref) {
        QWheelEvent* event = ref.value();
        return event->buttons();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to get buttons");
    return {};
}
#


JNIEXPORT jint JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWheelEvent_getSource_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWheelEvent>(env, obj);
    if (ref) {
        QWheelEvent* event = ref.value();
        return event->source();
    }
    ThrowNewError(env, LOG_PREFIX + "Failed to get source");
    return {};
}

