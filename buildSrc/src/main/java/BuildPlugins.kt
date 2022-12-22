object BuildPlugins {
    const val ANDROID_APPLICATION_PLUGIN = "com.android.application"
    const val JETBRAINS_KOTLIN_ANDROID_PLUGIN = "org.jetbrains.kotlin.android"
    const val KOTLIN_KAPT = "kotlin-kapt"
    const val KTLINT = "org.jlleitschuh.gradle.ktlint"
    const val KOTLIN_PARCELIZE = "kotlin-parcelize"
    const val SAFE_ARGS = "androidx.navigation.safeargs"
    const val DAGGER_HILT = "dagger.hilt.android.plugin"

    private const val TOOLS_BUILD_VERSION = "7.1.3"
    const val TOOLS_BUILD_GRADLE =
        "com.android.tools.build:gradle:$TOOLS_BUILD_VERSION"

    private const val KOTLIN_GRADLE = "1.6.10"
    const val KOTLIN_GRADLE_PLUGIN =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_GRADLE"
}