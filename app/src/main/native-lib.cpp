//
// Created by Martin Oliva on 17/10/22.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_antares_justcinema_Keys_apiKey(JNIEnv *env, jobject object) {
    std::string api_key = "9f6c3d84f781a5abd066dda6531609f1";
    return env->NewStringUTF(api_key.c_str());
}
