import org.gradle.api.JavaVersion

object ProjectProperties {
    const val COMPILE_SDK = 33
    const val TARGET_SDK = COMPILE_SDK
    const val MIN_SDK = 21
    const val APPLICATION_ID = "com.jxareas.xpensor"

    const val IS_MINIFY_ENABLED = false
    const val JVM_TARGET = "1.8"
    val JAVA_VERSION = JavaVersion.VERSION_1_8

}