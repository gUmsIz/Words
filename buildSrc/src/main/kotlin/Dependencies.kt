const val kotlinVersion = "1.6.10"
const val navigationVersion = "2.5.0-alpha01"

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "7.1.0"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val navigationGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinNavigationSafeargs = "androidx.navigation.safeargs.kotlin"

}

object AndroidSdk {
    const val minSdk     = 16
    const val compileSdk = 31
    const val targetSdk  = compileSdk
}

object Libraries {
    private object Versions {
        const val viewpager2        = "1.0.0"
        const val appcompat         = "1.4.1"
        const val ktx               = "1.7.0"
        const val constraintlayout  = "2.1.3"
        const val legacy_support_v4 = "1.0.0"
        const val lifecycle         = "2.5.0-alpha01"
        const val recyclerview      = "1.2.1"
        const val room              = "2.4.1"
        const val retrofit          = "2.9.0"
        const val material          = "1.6.0-alpha02"
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