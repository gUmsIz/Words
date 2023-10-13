const val kotlinVersion = "1.9.10"
const val navigationVersion = "2.5.0"
const val realmKotlinVersion = "1.11.0"

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "8.1.2"
        const val composeCompilerVersin = "1.5.3"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val navigationGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinNavigationSafeargs = "androidx.navigation.safeargs.kotlin"
    const val androidLibrary = "com.android.library"
    const val multiplatform = "multiplatform"
    const val serialization = "plugin.serialization"
    const val realm = "io.realm.kotlin"

}

object AndroidSdk {
    const val minSdk     = 21
    const val compileSdk = 34
    const val targetSdk  = compileSdk
}

object Libraries {
    private object Versions {
        const val viewpager2        = "1.0.0"
        const val appcompat         = "1.4.1"
        const val ktx               = "1.7.0"
        const val constraintlayout  = "2.1.3"
        const val legacy_support_v4 = "1.0.0"
        const val lifecycle         = "2.6.0"
        const val recyclerview      = "1.2.1"
        const val room              = "2.6.0-rc01"
        const val retrofit          = "2.9.0"
        const val material          = "1.6.0-alpha02"
        const val compose_activity  = "1.3.1"
        const val compose_material  = "1.1.0-rc03"
        const val compose_view_model= "1.0.0-alpha07"
        const val compose_nav       = "2.5.0"
        const val koin              = "3.4.0"
        const val koin_compose      = "3.4.3"
        const val realm_kotlin_base = "1.11.0"
        const val coroutine_core    = "1.7.3"
        const val serialization     = "1.6.0"
    }

    const val kotlinStdLib          = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val viewPager2            = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    const val appCompat             = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val ktxCore               = "androidx.core:core-ktx:${Versions.ktx}"
    const val constraintLayout      = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val legacySupport         = "androidx.legacy:legacy-support-v4:${Versions.legacy_support_v4}"
    const val lifecycleViewmodel    = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveData     = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val recyclerView          = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val navigationFragment    = "androidx.navigation:navigation-fragment-ktx:${navigationVersion}"
    const val navigationUi          = "androidx.navigation:navigation-ui-ktx:${navigationVersion}"
    const val roomRuntime           = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx               = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler          = "androidx.room:room-compiler:${Versions.room}"
    const val retrofit              = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson          = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val material              = "com.google.android.material:material:${Versions.material}"
    // Integration with activities
    const val composeActivity       = "androidx.activity:activity-compose:${Versions.compose_activity}"
    // Compose Material Design
    const val composeMaterial       = "androidx.compose.material:material:${Versions.compose_material}"
    // Animations
    const val composeAnimation      = "androidx.compose.animation:animation:${Versions.compose_material}"
    // Tooling support (Previews, etc.)
    const val composeUi             = "androidx.compose.ui:ui-tooling:${Versions.compose_material}"
    const val composeRuntime        = "androidx.compose.runtime:runtime:${Versions.compose_material}"
    const val composeRuntimeLivedata= "androidx.compose.runtime:runtime-livedata:${Versions.compose_material}"
    // Integration with ViewModels
    const val composeViewmodel      = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"

    const val composeNavigation     = "androidx.navigation:navigation-compose:${Versions.compose_nav}"
    const val koin_core             = "io.insert-koin:koin-core:${Versions.koin}"
    const val koin_android          = "io.insert-koin:koin-android:${Versions.koin}"
    const val koin_android_compose  = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    //KMM Libs
    const val realm_kotlin_base     = "io.realm.kotlin:library-base:${Versions.realm_kotlin_base}"
    const val coroutine_core        = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine_core}"
    const val serialization         = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"
}

object TestLibraries {
    private object Versions {
        const val junit4    = "4.13.2"
        const val junit     = "1.1.3"
        const val espresso  = "3.4.0"
    }
    const val junit4     = "junit:junit:${Versions.junit4}"
    const val junit      = "androidx.test.ext:junit:${Versions.junit}"
    const val espresso   = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}