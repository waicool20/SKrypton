#include <jni_utils.h>

namespace PrivateFieldUtils {
    bool HandleFieldAccessException(JNIEnv* env, string& fieldName) {
        if (CheckExceptions(env)) {
            cerr << "Error while accessing field: " + fieldName << endl;
            return true;
        }
        return false;
    }

    bool HandleStaticFieldAccessException(JNIEnv* env, string& fieldName) {
        if (CheckExceptions(env)) {
            cerr << "Error while accessing static field: " + fieldName << endl;
            return true;
        }
        return false;
    }
}

optional<jfieldID> GetFieldID(JNIEnv* env, jobject obj, string fieldName, string type) {
    auto clazz = env->GetObjectClass(obj);
    auto id = env->GetFieldID(clazz, fieldName.c_str(), type.c_str());
    if (CheckExceptions(env)) {
        cerr << LOG_PREFIX + "Error while getting fieldID for field: " + fieldName + " with type: " + type << endl;
        return {};
    }
    return { id };
}

optional<jfieldID> GetStaticFieldID(JNIEnv* env, jobject obj, string fieldName, string type) {
    return GetStaticFieldID(env, env->GetObjectClass(obj), fieldName, type);
}

optional<jfieldID> GetStaticFieldID(JNIEnv* env, jclass clazz, string fieldName, string type) {
    auto id = env->GetStaticFieldID(clazz, fieldName.c_str(), type.c_str());
    if (CheckExceptions(env)) {
        cerr << LOG_PREFIX + "Error while getting static fieldID for field: " + fieldName + " with type: " + type << endl;
        return {};
    }
    return { id };
}

//<editor-fold desc="Get(Static)ObjectFieldValue helper functions implementations">

optional<jobject> GetObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type) {
    replace(type.begin(), type.end(), '.', '/');
    type.insert(0, "L");
    type.push_back(';');

    auto field = GetFieldID(env, obj, fieldName.c_str(), type.c_str());
    if (!field) return {};

    auto value = env->GetObjectField(obj, field.value());
    if (CheckExceptions(env)) {
        cerr << LOG_PREFIX + "Error while trying to access field: " + fieldName + " with type: " + type << endl;
        return {};
    }
    return { value };
}

optional<jobject> GetStaticObjectFieldValue(JNIEnv* env, jobject obj, string fieldName, string type) {
    jclass clazz = env->GetObjectClass(obj);
    replace(type.begin(), type.end(), '.', '/');
    type.insert(0, "L");
    type.push_back(';');

    auto sField = env->GetStaticFieldID(clazz, fieldName.c_str(), type.c_str());
    if (CheckExceptions(env)) {
        cerr << LOG_PREFIX + "Error while getting static fieldID for field: " + fieldName + " with type: " + type << endl;
        return {};
    }

    auto value = (env->GetStaticObjectField(clazz, sField));
    if (CheckExceptions(env)) {
        cerr << LOG_PREFIX + "Error while trying to access static field: " + fieldName + " with type: " + type << endl;
        return {};
    }
    return { value };
}

//</editor-fold>
