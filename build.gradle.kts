// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(BuildPlugins.TOOLS_BUILD_GRADLE)
        classpath(BuildPlugins.KOTLIN_GRADLE_PLUGIN)
        classpath(Dependencies.Navigation.SAFE_ARGS_PLUGIN)
        classpath(Dependencies.Dagger.HILT_PLUGIN)
        classpath(BuildPlugins.VERSIONS_PLUGIN)
        classpath(BuildPlugins.DETEKT_PLUGIN)
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
        .version(Versions.KTLINT_PLUGIN)
        .apply(false)
    id(BuildPlugins.DETEKT)
        .version(Versions.DETEKT)
}

apply(from = BuildScripts.HOOKS)
subprojects {
    apply(from = "${rootProject.projectDir}/${BuildScripts.KTLINT}")
    apply(from = "${rootProject.projectDir}/${BuildScripts.VERSIONS}")
}

tasks {
    val detektAll by registering(io.gitlab.arturbosch.detekt.Detekt::class) {
        parallel = true
        setSource(files(projectDir))
        include("**/*.kt")
        include("**/*.kts")
        exclude("**/resources/**")
        exclude("**/build/**")
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        buildUponDefaultConfig = false
    }
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}
