#include <SKryptonMouseEvent.h>

jlong Java_com_waicool20_skrypton_jni_objects_SKryptonMouseEvent_getButton_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QMouseEvent>(env, obj);
    if (opt) {
        QMouseEvent* event = opt.value();
        return (jlong) event->button();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get button");
}

jlong Java_com_waicool20_skrypton_jni_objects_SKryptonMouseEvent_getButtons_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QMouseEvent>(env, obj);
    if (opt) {
        QMouseEvent* event = opt.value();
        return (jlong) event->buttons();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get buttons");
}

jobject Java_com_waicool20_skrypton_jni_objects_SKryptonMouseEvent_getGlobalPos_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QMouseEvent>(env, obj);
    if (opt) {
        QMouseEvent* event = opt.value();
        auto point = NewObject(env, "java.awt.Point", "(II)V", event->globalX(), event->globalY());
        if (point) return point.value();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get global pos");
}

jobject Java_com_waicool20_skrypton_jni_objects_SKryptonMouseEvent_getScreenPos_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QMouseEvent>(env, obj);
    if (opt) {
        QMouseEvent* event = opt.value();
        auto pos = event->screenPos();
        auto point = NewObject(env, "java.awt.Point", "(II)V", pos.x(), pos.y());
        if (point) return point.value();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get screen pos");
}

jobject Java_com_waicool20_skrypton_jni_objects_SKryptonMouseEvent_getLocalPos_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QMouseEvent>(env, obj);
    if (opt) {
        QMouseEvent* event = opt.value();
        auto point = NewObject(env, "java.awt.Point", "(II)V", event->x(), event->y());
        if (point) return point.value();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get local pos");
}

jint Java_com_waicool20_skrypton_jni_objects_SKryptonMouseEvent_getSource_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QMouseEvent>(env, obj);
    if (opt) {
        QMouseEvent* event = opt.value();
        return (jint) event->source();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get button");
}

jlong
Java_com_waicool20_skrypton_jni_objects_SKryptonMouseEvent_00024Companion_initialize_1N(JNIEnv* env, jobject obj,
                                                                                        jint e_type,
                                                                                        jint localPosX, jint localPosY,
                                                                                        jint windowPosX,
                                                                                        jint windowPosY,
                                                                                        jint screenPosX,
                                                                                        jint screenPosY,
                                                                                        jlong e_button, jlong e_buttons,
                                                                                        jlong e_modifiers,
                                                                                        jint e_source) {
    auto type = static_cast<QEvent::Type>(e_type);
    auto localPos = QPoint { localPosX, localPosY };
    auto windowPos = QPoint { windowPosX, windowPosY };
    auto screenPos = QPoint { screenPosX, screenPosY };
    auto button = static_cast<Qt::MouseButton>(e_button);
    auto buttons = static_cast<Qt::MouseButton>(e_buttons);
    auto modifiers = static_cast<Qt::KeyboardModifier>(e_modifiers);
    auto source = static_cast<Qt::MouseEventSource>(e_source);

    return (jlong) new QMouseEvent { type, localPos, windowPos, screenPos, button, buttons, modifiers, source };
}

void Java_com_waicool20_skrypton_jni_objects_SKryptonMouseEvent_dispose_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QMouseEvent>(env, obj);
    if (opt) delete opt.value();
}
