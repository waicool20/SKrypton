#include <JQWidget.h>

void Java_com_waicool20_skrypton_jni_objects_QWidget_show_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        RunOnMainThread([=] { widget->show(); });
    }
}

void Java_com_waicool20_skrypton_jni_objects_QWidget_hide_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        RunOnMainThread([=] { widget->hide(); });
    }
}

void Java_com_waicool20_skrypton_jni_objects_QWidget_resize_1N(JNIEnv* env, jobject obj, jint width, jint height) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        RunOnMainThread([=] { widget->resize((int) width, (int) height); });
    }
}

