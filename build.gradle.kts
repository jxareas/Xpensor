// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(BuildPlugins.TOOLS_BUILD_GRADLE)
        classpath(BuildPlugins.KOTLIN_GRADLE_PLUGIN)
        classpath(Dependencies.Navigation.NAVIGATION_SAFE_ARGS)
        classpath(Dependencies.Dagger.HILT_PLUGIN)
    }
}

plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
        .version(ProjectProperties.ANDROID_VERSION)
        .apply(false)
    id(BuildPlugins.ANDROID_APPLICATION)
        .version(ProjectProperties.ANDROID_VERSION)
        .apply(false)
    id(BuildPlugins.JETBRAINS_KOTLIN_ANDROID)
        .version(ProjectProperties.KOTLIN_VERSION)
        .apply(false)
    id(BuildPlugins.KTLINT)
        .version(Versions.KTLINT)
        .apply(false)
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}