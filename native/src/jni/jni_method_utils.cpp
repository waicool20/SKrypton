#include <jni_utils.h>

namespace PrivateMethodUtils {
    bool HandleMethodIDException(JNIEnv* env, jobject obj, string& methodName, string& signature) {
        if (CheckExceptions(env)) {
            cerr << LOG_PREFIX + "Failed method call for method \"" + methodName +
                    "\" on object of type: " + GetClassName(env, obj).value_or("Unknown Class") +
                    " with signature \"" + signature + "\"" << endl;
            return true;
        }
        return false;
    }
}
