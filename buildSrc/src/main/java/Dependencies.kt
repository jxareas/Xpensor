object Dependencies {
    object Kotlin {
        const val KOTLINX_COROUTINES =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES}"
    }

    object Android {

        const val KOTLIN_CORE =
            "androidx.core:core-ktx:${Versions.KOTLIN_CORE_KTX}"

        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
        const val LEGACY_SUPPORT =
            "androidx.legacy:legacy-support-v4:${Versions.LEGACY_SUPPORT_VERSION}"

        const val SPLASH_SCREEN = "androidx.core:core-splashscreen:${Versions.SPLASH_SCREEN}"
        const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
        const val MATERIAL =
            "com.google.android.material:material:${Versions.MATERIAL}"
        const val CARDVIEW = "androidx.cardview:cardview:${Versions.CARDVIEW}"

        const val CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
        const val SWIPE_REFRESH_LAYOUT =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.SWIPE_REFRESH_LAYOUT}"

        const val RECYCLERVIEW =
            "androidx.recyclerview:recyclerview:${Versions.RECYCLERVIEW}"
        const val VIEWPAGER2 = "androidx.viewpager2:viewpager2:${Versions.VIEWPAGER2}"

        const val DESUGARING_CORE_LIB =
            "com.android.tools:desugar_jdk_libs:${Versions.DESUGAR_JDK_LIB}"

        const val LIFECYCLE_LIVE_DATA_KTX =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_KTX_VERSION}"
        const val LIFECYCLE_VIEWMODEL_KTX =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_KTX_VERSION}"
        const val PALETTE = "androidx.palette:palette:${Versions.PALETTE}"
    }

    object Testing {
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val JUNIT_ANDROID = "androidx.test.ext:junit:${Versions.JUNIT_ANDROID_EXT}"
        const val ESPRESSO_ANDROID =
            "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_ANDROID_CORE}"
    }

    object Navigation {
        const val SAFE_ARGS_PLUGIN =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION}"
        const val FRAGMENT_KTX =
            "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
        const val UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    }

    object DataStore {
        const val DATA_STORE =
            "androidx.datastore:datastore:${Versions.DATA_STORE}"
    }

    object Dagger {
        const val HILT =
            "com.google.dagger:hilt-android:${Versions.DAGGER_HILT}"
        const val HILT_PLUGIN =
            "com.google.dagger:hilt-android-gradle-plugin:${Versions.DAGGER_HILT}"
        const val HILT_COMPILER =
            "com.google.dagger:hilt-compiler:${Versions.DAGGER_HILT}"
    }

    object Room {
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    }

    object Preference {
        const val PREFERENCEX = "androidx.preference:preference:${Versions.PREFERENCE}"
    }

    object Square {
        const val OKHTTP_CLIENT = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP3}"
        const val LOGGING_INTERCEPTOR =
            "com.squareup.okhttp3:logging-interceptor:${Versions.HTTP_LOGGING_INTERCEPTOR}"

        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT2}"
        const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
        const val MOSHI_CONVERTER =
            "com.squareup.retrofit2:converter-moshi:${Versions.MOSHI_CONVERTER}"
    }

    object WorkManager {
        const val WORK_MANAGER_RUNTIME = "androidx.work:work-runtime:${Versions.WORK_MANAGER}"
        const val WORK_MANAGER_RUNTIME_KTX =
            "androidx.work:work-runtime-ktx:${Versions.WORK_MANAGER}"
    }

    object AirBnb {
        const val LOTTIE = "com.airbnb.android:lottie:${Versions.LOTTIE}"
    }

    object PhilJay {
        const val MP_ANDROID_CHART =
            "com.github.PhilJay:MPAndroidChart:${Versions.MP_ANDROID_CHART}"
    }

}