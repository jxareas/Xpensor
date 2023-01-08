import org.gradle.api.JavaVersion

object ProjectProperties {
    // SDK
    const val COMPILE_SDK = 33
    const val TARGET_SDK = COMPILE_SDK
    const val MIN_SDK = 21

    // Android & Kotlin Versions
    const val ANDROID_VERSION = "7.2.2"
    const val KOTLIN_VERSION = "1.7.10"

    // Application
    const val APPLICATION_ID = "com.jxareas.xpensor"
    const val VERSION_CODE = 3
    const val VERSION_NAME = "0.1.0-alpha02"

    // ProGuard
    const val PROGUARD_NAME = "proguard-android.txt"
    const val PROGUARD_FILE = "proguard-rules.pro"

    // Configuration
    const val IS_VECTOR_DRAWABLES_SUPPORT_ENABLED = true
    const val IS_MULTIDEX_ENABLED = true
    const val IS_MINIFY_ENABLED = false
    const val IS_VIEW_BINDING_ENABLED = true
    const val IS_CORE_LIBRARY_DESUGARING_ENABLED = true

    // Java
    const val TEST_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val JVM_TARGET = "1.8"
    val JAVA_VERSION = JavaVersion.VERSION_1_8

    // BuildTypes
    const val RELEASE_BUILD_TYPE = "release"
    const val DEBUG_BUILD_TYPE = "debug"

    // API Keys
    const val CURRENCY_API_KEY = "CURRENCY_API_KEY"

}