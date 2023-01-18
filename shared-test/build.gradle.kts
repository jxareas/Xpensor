plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.JETBRAINS_KOTLIN_ANDROID)
}

android {
    namespace = ProjectProperties.SHARED_TEST_NAMESPACE
    compileSdk = ProjectProperties.COMPILE_SDK

    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK
        targetSdk = ProjectProperties.TARGET_SDK

        testInstrumentationRunner = ProjectProperties.TEST_RUNNER_ANDROID_JUNIT
        consumerProguardFiles(ProjectProperties.PROGUARD_CONSUMER_RULES)
    }

    buildTypes {
        release {
            isMinifyEnabled = ProjectProperties.IS_MINIFY_ENABLED
            proguardFiles(
                getDefaultProguardFile(ProjectProperties.PROGUARD_ANDROID_OPTIMIZE),
                ProjectProperties.PROGUARD_RULES,
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

    implementation(project(ProjectProperties.APP_PATH))
    implementation(Dependencies.Android.KOTLIN_CORE_KTX)
    implementation(Dependencies.Android.APP_COMPAT)
    implementation(Dependencies.Android.APP_COMPAT)
    implementation(Dependencies.Testing.JUNIT)
    implementation(Dependencies.Kotlin.COROUTINES_TEST)
    coreLibraryDesugaring(Dependencies.Android.DESUGARING_CORE_LIB)
}
