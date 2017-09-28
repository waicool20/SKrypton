#include <WebViewHighlighter.h>

const int alpha = 120;

JNIEXPORT jlong JNICALL
Java_com_waicool20_skrypton_jni_objects_WebViewHighlighter_00024Companion_initialize_1N(JNIEnv* env, jobject obj,
                                                                                        jobject view,
                                                                                        jint x, jint y,
                                                                                        jint width, jint height,
                                                                                        jboolean fillColor, jint red,
                                                                                        jint green, jint blue) {
    auto ref = PointerFromCPointer<SKryptonWebViewContainer>(env, view);
    if (ref) {
        SKryptonWebViewContainer* container = ref.value();
        WebViewHighlighter* highlighter = nullptr;
        SKryptonApp::runOnMainThread([&] {
            highlighter = new WebViewHighlighter(container, x, y, width, height, fillColor, red, green, blue);
        });
        while (!highlighter) {}
        return (jlong) highlighter;
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get container ref");
    return {};
}

JNIEXPORT jobject JNICALL
Java_com_waicool20_skrypton_jni_objects_WebViewHighlighter_getColor_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<WebViewHighlighter>(env, obj);
    if (ref) {
        WebViewHighlighter* highlighter = ref.value();
        auto color = highlighter->getColor();
        auto jColor = NewObject(env, "java.awt.Color", "(III)V", color.red(), color.green(), color.blue());
        if (jColor) return jColor.value();
        ThrowNewError(env, LOG_PREFIX + "Could not create java color");
        return {};
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get color");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_WebViewHighlighter_setColor_1N(JNIEnv* env, jobject obj, jint red, jint green,
                                                                       jint blue) {
    auto ref = PointerFromCPointer<WebViewHighlighter>(env, obj);
    if (ref) {
        WebViewHighlighter* highlighter = ref.value();
        highlighter->setColor(QColor { red, green, blue, alpha });
        SKryptonApp::runOnMainThread([=]{
            highlighter->repaint();
        });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set color");
    }
}

JNIEXPORT jboolean JNICALL
Java_com_waicool20_skrypton_jni_objects_WebViewHighlighter_isFillColor_1N(JNIEnv* env, jobject obj) {
    auto ref = PointerFromCPointer<WebViewHighlighter>(env, obj);
    if (ref) {
        WebViewHighlighter* highlighter = ref.value();
        return highlighter->isFillColor();
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get if is fill color");
    return {};
}

JNIEXPORT void JNICALL
Java_com_waicool20_skrypton_jni_objects_WebViewHighlighter_setFillColor_1N(JNIEnv* env, jobject obj,
                                                                           jboolean fillColor) {
    auto ref = PointerFromCPointer<WebViewHighlighter>(env, obj);
    if (ref) {
        WebViewHighlighter* highlighter = ref.value();
        highlighter->setFillColor(fillColor);
        SKryptonApp::runOnMainThread([=]{
            highlighter->repaint();
        });
    } else {
        ThrowNewError(env, LOG_PREFIX + "Could not set if fill color");
    }
}

WebViewHighlighter::WebViewHighlighter(SKryptonWebViewContainer* parent, int x, int y, int width, int height,
                                       bool fillColor, int red, int green, int blue) :
        QWidget { parent }, color(QColor { red, green, blue, alpha }),
        fillColor(fillColor) {
    setAttribute(Qt::WA_NoSystemBackground);
    setAttribute(Qt::WA_TransparentForMouseEvents);
    setGeometry(x, y, width, height);
    hide();
}

void WebViewHighlighter::paintEvent(QPaintEvent* event) {
    QPainter painter { this };
    QPen pen { color, 5 };
    painter.setPen(pen);
    painter.drawRect(0, 0, width(), height());
    if (fillColor) painter.fillRect(0, 0, width(), height(), color.lighter(150));
}

const QColor& WebViewHighlighter::getColor() const {
    return color;
}

void WebViewHighlighter::setColor(const QColor& color) {
    this->color = color;
}

bool WebViewHighlighter::isFillColor() const {
    return fillColor;
}

void WebViewHighlighter::setFillColor(bool fillColor) {
    this->fillColor = fillColor;
}
