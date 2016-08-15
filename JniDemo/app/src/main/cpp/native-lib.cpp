#include <jni.h>
#include <string>

extern "C" {
JNIEXPORT jstring JNICALL
Java_com_example_rafaelbelleza_jnidemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */)
{
    std::string hello = "Hello from C++";
    jstring str = env->NewStringUTF(hello.c_str());
    return str;
}

JNIEXPORT void JNICALL
Java_com_example_rafaelbelleza_jnidemo_MainActivity_byteArrayInit(
        JNIEnv *env,
        jobject /* this */,
        jbyteArray byteArray)
{
    jsize length = env->GetArrayLength(byteArray);
    jbyte* array = env->GetByteArrayElements(byteArray, NULL);

    for (jsize i = 0; i < length; ++i)
    {
        array[i] = (jbyte) i;
    }

    env->ReleaseByteArrayElements(byteArray, array, 0);
}
}
