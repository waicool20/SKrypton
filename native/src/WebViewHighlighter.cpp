#include <WebViewHighlighter.h>

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
        SKryptonApp::runOnMainThread([&]{
            highlighter = new WebViewHighlighter(container, x, y, width, height, fillColor, red, green, blue);
        });
        while (!highlighter) {}
        return (jlong) highlighter;
    }
    ThrowNewError(env, LOG_PREFIX + "Could not get container ref");
    return {};
}

WebViewHighlighter::WebViewHighlighter(SKryptonWebViewContainer* parent, int x, int y, int width, int height,
                                       bool fillColor, int red, int green, int blue) :
        QWidget { parent }, color(QColor { red, green, blue, 120 }),
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

