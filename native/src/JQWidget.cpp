#include <JQWidget.h>

void Java_com_waicool20_skrypton_jni_objects_QWidget_show_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        SKryptonApp::runOnMainThread([=] { widget->show(); });
    }
}

void Java_com_waicool20_skrypton_jni_objects_QWidget_hide_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        SKryptonApp::runOnMainThread([=] { widget->hide(); });
    }
}

void Java_com_waicool20_skrypton_jni_objects_QWidget_resize_1N(JNIEnv* env, jobject obj, jint width, jint height) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        SKryptonApp::runOnMainThread([=] { widget->resize((int) width, (int) height); });
    }
}

void Java_com_waicool20_skrypton_jni_objects_QWidget_dispose_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWidget>(env, obj);
    auto className = GetClassName(env, obj);
    if (opt) {
        QWidget* view = opt.value();
        SKryptonApp::runOnMainThread([=] { view->close(); });
    } else {
        ThrowNewError("com.waicool20.skrypton.util.DisposeFailException",
                      "Failed to dispose an instance: " + className.value_or("Could not get Class information"));
    }
}

