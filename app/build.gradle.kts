import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.JETBRAINS_KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.SAFE_ARGS)
    id(BuildPlugins.KOTLIN_PARCELIZE)
    id(BuildPlugins.DAGGER_HILT)
}

android {
    compileSdk = ProjectProperties.COMPILE_SDK

    defaultConfig {
        namespace = ProjectProperties.APPLICATION_ID
        minSdk = ProjectProperties.MIN_SDK
        targetSdk = ProjectProperties.TARGET_SDK
        versionCode = ProjectProperties.VERSION_CODE
        versionName = ProjectProperties.VERSION_NAME
        multiDexEnabled = ProjectProperties.IS_MULTIDEX_ENABLED
        vectorDrawables.useSupportLibrary = ProjectProperties.IS_VECTOR_DRAWABLES_SUPPORT_ENABLED
        testInstrumentationRunner = ProjectProperties.TEST_RUNNER_ANDROID_JUNIT

        val currencyApiKey = ProjectProperties.CURRENCY_API_KEY
        // Read CURRENCY_API_TOKEN key from local.properties
        val currencyApiToken: String = gradleLocalProperties(rootDir).getProperty(currencyApiKey)
        buildConfigField("String", currencyApiKey, currencyApiToken)

        javaCompileOptions {
            annotationProcessorOptions {
                arguments[ProjectProperties.ROOM_SCHEMA_LOCATION] =
                    "$projectDir/${ProjectProperties.ROOM_SCHEMA_DIR}"
            }
        }
    }

    buildTypes {
        getByName(ProjectProperties.RELEASE_BUILD_TYPE) {
            isMinifyEnabled = ProjectProperties.IS_MINIFY_ENABLED
        }
        getByName(ProjectProperties.DEBUG_BUILD_TYPE) {
            proguardFiles(
                getDefaultProguardFile(ProjectProperties.PROGUARD_ANDROID),
                ProjectProperties.PROGUARD_RULES
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
    buildFeatures {
        viewBinding = ProjectProperties.IS_VIEW_BINDING_ENABLED
    }
    android {
        compileOptions {
            isCoreLibraryDesugaringEnabled = ProjectProperties.IS_CORE_LIBRARY_DESUGARING_ENABLED
        }
    }
}

dependencies {

    // Support Libraries
    implementation(Dependencies.Android.KOTLIN_CORE)
    implementation(Dependencies.Android.APP_COMPAT)
    implementation(Dependencies.Android.LEGACY_SUPPORT)
    implementation(Dependencies.Android.FRAGMENT_KTX)

    // Desugaring
    coreLibraryDesugaring(Dependencies.Android.DESUGARING_CORE_LIB)

    // Testing
    testImplementation(project(ProjectProperties.SHARED_TEST_PATH))
    androidTestImplementation(project(ProjectProperties.SHARED_TEST_PATH))
    testImplementation(Dependencies.Testing.TRUTH)
    androidTestImplementation(Dependencies.Testing.TRUTH)
    testImplementation(Dependencies.Testing.JUNIT)
    androidTestImplementation(Dependencies.Testing.JUNIT_EXT)
    androidTestImplementation(Dependencies.Testing.ESPRESSO_CORE)
    testImplementation(Dependencies.Testing.ANDROID_TEST_CORE)
    testImplementation(Dependencies.Testing.ANDROID_ARCH_TEST_CORE)
    testImplementation(Dependencies.Testing.TURBINE)
    testImplementation(Dependencies.Mockito.KOTLIN)
    testImplementation(Dependencies.Mockito.CORE)
    testImplementation(Dependencies.Mockito.INLINE)
    testImplementation(Dependencies.Mockito.MOCKK)

    // AndroidX
    implementation(Dependencies.Android.SPLASH_SCREEN)
    implementation(Dependencies.Android.MATERIAL)
    implementation(Dependencies.Android.CARDVIEW)
    implementation(Dependencies.Android.CONSTRAINT_LAYOUT)
    implementation(Dependencies.Android.SWIPE_REFRESH_LAYOUT)
    implementation(Dependencies.Android.RECYCLERVIEW)
    implementation(Dependencies.Android.VIEWPAGER2)

    // Kotlin Coroutines
    implementation(Dependencies.Kotlin.KOTLINX_COROUTINES)
    testImplementation(Dependencies.Kotlin.KOTLIN_COROUTINES_TEST)
    androidTestImplementation(Dependencies.Kotlin.KOTLIN_COROUTINES_TEST)

    // Dagger-Hilt
    implementation(Dependencies.Dagger.HILT)
    kapt(Dependencies.Dagger.HILT_COMPILER)

    // Data Store
    implementation(Dependencies.DataStore.DATA_STORE)

    // Navigation
    implementation(Dependencies.Navigation.FRAGMENT_KTX)
    implementation(Dependencies.Navigation.UI_KTX)
    androidTestImplementation(Dependencies.Navigation.TESTING)

    // Lifecycle
    implementation(Dependencies.Android.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Dependencies.Android.LIFECYCLE_LIVE_DATA_KTX)

    // ROOM
    implementation(Dependencies.Room.ROOM_RUNTIME)
    implementation(Dependencies.Room.ROOM_KTX)
    kapt(Dependencies.Room.ROOM_COMPILER)

    // Preferences
    implementation(Dependencies.Preferences.PREFERENCEX)

    // OkHttp3
    implementation(Dependencies.Square.OKHTTP_CLIENT)
    implementation(Dependencies.Square.LOGGING_INTERCEPTOR)
    // Retrofit
    implementation(Dependencies.Square.RETROFIT)
    implementation(Dependencies.Square.MOSHI_CONVERTER)
    // Moshi
    implementation(Dependencies.Square.MOSHI)

    // Palette
    implementation(Dependencies.Android.PALETTE)

    // Lottie
    implementation(Dependencies.AirBnb.LOTTIE)

    // Work Manager
    implementation(Dependencies.WorkManager.WORK_MANAGER_RUNTIME)
    implementation(Dependencies.WorkManager.WORK_MANAGER_RUNTIME_KTX)

    // MPAndroidChart
    implementation(Dependencies.PhilJay.MP_ANDROID_CHART)
}
