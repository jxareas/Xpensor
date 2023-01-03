plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.JETBRAINS_KOTLIN_ANDROID)
}

android {
    namespace = ProjectProperties.LIBRARY_SHARED_TEST_NAMESPACE
    compileSdk = ProjectProperties.COMPILE_SDK

    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK
        targetSdk = ProjectProperties.TARGET_SDK

        testInstrumentationRunner = ProjectProperties.TEST_RUNNER
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = ProjectProperties.IS_MINIFY_ENABLED
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = ProjectProperties.JAVA_VERSION
        targetCompatibility = ProjectProperties.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = ProjectProperties.JVM_TARGET
    }
    android {
        compileOptions {
            isCoreLibraryDesugaringEnabled = ProjectProperties.IS_CORE_LIBRARY_DESUGARING_ENABLED
        }
    }
}

dependencies {
    implementation(project(path = ":app"))
    implementation(Dependencies.Android.KOTLIN_CORE)
    implementation(Dependencies.Android.APP_COMPAT)
    implementation(Dependencies.Android.MATERIAL)
    implementation(Dependencies.Testing.JUNIT)
    implementation(Dependencies.Testing.JUNIT_EXT)
    implementation(Dependencies.Testing.ESPRESSO_CORE)
    implementation(Dependencies.Kotlin.KOTLINX_COROUTINES)
    // Desugaring
    implementation(Dependencies.Kotlin.KOTLIN_COROUTINES_TEST)
    coreLibraryDesugaring(Dependencies.Android.DESUGARING_CORE_LIB)
}
