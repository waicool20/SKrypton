/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_waicool20_skrypton_jni_objects_SKryptonWebView */

#ifndef _Included_com_waicool20_skrypton_jni_objects_SKryptonWebView
#define _Included_com_waicool20_skrypton_jni_objects_SKryptonWebView
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    initialize_N
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_initialize_1N
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    load_N
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_load_1N
  (JNIEnv *, jobject, jstring);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    loadHtml_N
 * Signature: (Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_loadHtml_1N
  (JNIEnv *, jobject, jstring, jstring);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    back_N
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_back_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    forward_N
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_forward_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    reload_N
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_reload_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    stop_N
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_stop_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    isLoading_N
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_isLoading_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    getSettings_N
 * Signature: ()Lcom/waicool20/skrypton/jni/objects/SKryptonWebSettings;
 */
JNIEXPORT jobject JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_getSettings_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    setZoomFactor_N
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_setZoomFactor_1N
  (JNIEnv *, jobject, jdouble);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    zoomFactor_N
 * Signature: ()D
 */
JNIEXPORT jdouble JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_zoomFactor_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    isShowingCursor_N
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_isShowingCursor_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    setShowingCursor_N
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_setShowingCursor_1N
  (JNIEnv *, jobject, jboolean);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    getCursorX_N
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_getCursorX_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    getCursorY_N
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_getCursorY_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    url_N
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_url_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    runJavaScript_N
 * Signature: (Ljava/lang/String;Ljava/lang/Runnable;)V
 */
JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_runJavaScript_1N
  (JNIEnv *, jobject, jstring, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    takeScreenshot_N
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_takeScreenshot_1N
  (JNIEnv *, jobject);

/*
 * Class:     com_waicool20_skrypton_jni_objects_SKryptonWebView
 * Method:    sendEvent_N
 * Signature: (Lcom/waicool20/skrypton/jni/objects/SKryptonEvent;)V
 */
JNIEXPORT void JNICALL Java_com_waicool20_skrypton_jni_objects_SKryptonWebView_sendEvent_1N
  (JNIEnv *, jobject, jobject);

#ifdef __cplusplus
}
#endif
#endif
