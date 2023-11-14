const val kotlinVersion = "1.9.10"
const val realmKotlinVersion = "1.11.0"

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "8.1.2"
        const val composeCompilerVersin = "1.5.3"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinNavigationSafeargs = "androidx.navigation.safeargs.kotlin"
    const val androidLibrary = "com.android.library"
    const val multiplatform = "multiplatform"
    const val serialization = "plugin.serialization"
    const val realm = "io.realm.kotlin"

}

object AndroidSdk {
    const val minSdk = 21
    const val compileSdk = 34
    const val targetSdk = compileSdk
}

object Libraries {
    private object Versions {
        const val ktx = "1.7.0"
        const val lifecycle = "2.6.0"
        const val compose_activity = "1.8.0"
        const val compose = "1.5.4"
        const val compose_view_model = "1.0.0-alpha07"
        const val compose_nav = "2.5.0"
        const val koin = "3.4.0"
        const val koin_compose = "3.4.3"
        const val realm_kotlin_base = "1.11.0"
        const val coroutine_core = "1.7.3"
        const val serialization = "1.6.0"
        const val ktor = "2.3.5"
    }

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"

    // Integration with activities
    const val composeActivity = "androidx.activity:activity-compose:${Versions.compose_activity}"

    // Compose Material Design
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"

    // Animations
    const val composeAnimation = "androidx.compose.animation:animation:${Versions.compose}"

    // Tooling support (Previews, etc.)
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiToolingPrev = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"

    // Integration with ViewModels
    const val composeViewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"

    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.compose_nav}"
    const val koin_core = "io.insert-koin:koin-core:${Versions.koin}"
    const val koin_android = "io.insert-koin:koin-android:${Versions.koin}"
    const val koin_android_compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"

    //KMM Libs
    const val realm_kotlin_base = "io.realm.kotlin:library-base:${Versions.realm_kotlin_base}"
    const val coroutine_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine_core}"
    const val serialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
    const val ktor_client_core = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktor_client_cont = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    const val ktor_client_json = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val ktor_client_android = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
    const val ktor_client_native = "io.ktor:ktor-client-darwin:${Versions.ktor}"
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.13.2"
        const val junit = "1.1.3"
        const val espresso = "3.4.0"
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val junit = "androidx.test.ext:junit:${Versions.junit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}