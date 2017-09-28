#include <SKryptonWidget.h>

JNIEXPORT jobject JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWidget_getGeometry_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        auto jRectangle = NewObject(env, "java.awt.Rectangle", "(IIII)V", widget->x(), widget->y(), widget->width(),
                                    widget->height());
        if (jRectangle) return jRectangle.value();
    }
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWidget_setGeometry_1N(JNIEnv* env, jobject obj, jint x, jint y,
                                                                      jint width, jint height) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        SKryptonApp::runOnMainThread([=] { widget->setGeometry(x, y, width, height); });
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWidget_show_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        SKryptonApp::runOnMainThread([=] { widget->show(); });
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWidget_hide_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        SKryptonApp::runOnMainThread([=] { widget->hide(); });
    }
}

JNIEXPORT jboolean JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWidget_isHidden_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        return widget->isHidden();
    }
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWidget_move_1N(JNIEnv* env, jobject obj, jint x, jint y) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        SKryptonApp::runOnMainThread([=] { widget->move(x, y); });
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWidget_resize_1N(JNIEnv* env, jobject obj, jint width, jint height) {
    auto ref = PointerFromCPointer<QWidget>(env, obj);
    if (ref) {
        QWidget* widget = ref.value();
        SKryptonApp::runOnMainThread([=] { widget->resize((int) width, (int) height); });
    }
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_SKryptonWidget_dispose_1N(JNIEnv* env, jobject obj) {
    auto opt = PointerFromCPointer<QWidget>(env, obj);
    auto className = GetJClassName(env, obj);
    if (opt) {
        QWidget* view = opt.value();
        SKryptonApp::runOnMainThread([=] { view->close(); });
    } else {
        ThrowNewError("com.waicool20.skrypton.util.DisposeFailException",
                      "Failed to dispose an instance: " + className.value_or("Could not get Class information"));
    }
}


