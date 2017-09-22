#include <SKryptonKeyEvent.h>

JNIEXPORT jlong JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonKeyEvent_00024Companion_initialize_1N(JNIEnv* env, jobject obj,
                                                                                      jint e_type, jlong key,
                                                                                      jlong e_modifiers,
                                                                                      jstring character,
                                                                                      jboolean autoRepeat, jint count) {
    auto type = static_cast<QEvent::Type>(e_type);
    auto modifiers = static_cast<Qt::KeyboardModifier>(e_modifiers);
    return (jlong) new QKeyEvent { type, (int) key, modifiers, QString::fromStdString(StringFromJstring(env, character)),
                                   autoRepeat, (ushort) count };
}

JNIEXPORT jlong JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonKeyEvent_getKey_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QKeyEvent>(env, obj);
    if (opt) {
        QKeyEvent* event = opt.value();
        return event->key();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get key");
    return {};
}

JNIEXPORT jstring JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonKeyEvent_getChar_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QKeyEvent>(env, obj);
    if (opt) {
        QKeyEvent* event = opt.value();
        return JstringFromString(env, event->text().toStdString());
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get character");
    return {};
}

JNIEXPORT jboolean JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonKeyEvent_isAutoRepeat_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QKeyEvent>(env, obj);
    if (opt) {
        QKeyEvent* event = opt.value();
        return event->key();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get key");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonKeyEvent_dispose_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QKeyEvent>(env, obj);
    if (opt) delete opt.value();
}
