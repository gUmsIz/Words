plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinNavigationSafeargs)
}

//TODO clean old dependencies

android {
    compileSdk = AndroidSdk.compileSdk

    defaultConfig {
        applicationId = "com.gumsiz.words"
        minSdk = AndroidSdk.minSdk
        targetSdk = AndroidSdk.targetSdk
        versionCode = 5
        versionName = "1.3"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjvm-default=all")
            }
        }
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildPlugins.Versions.composeCompilerVersin
    }
    namespace = "com.gumsiz.words"
}

dependencies {
    implementation(Libraries.viewPager2)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.ktxCore)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.legacySupport)
    implementation(Libraries.lifecycleLiveData)
    implementation(Libraries.lifecycleViewmodel)
    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.junit)
    androidTestImplementation(TestLibraries.espresso)
    implementation(Libraries.recyclerView)

    implementation(Libraries.navigationFragment)
    implementation(Libraries.navigationUi)

    implementation(Libraries.material)

    implementation(Libraries.composeActivity)
    implementation(Libraries.composeAnimation)
    implementation(Libraries.composeMaterial)
    implementation(Libraries.composeUi)
    implementation(Libraries.composeViewmodel)
    implementation(Libraries.composeRuntime)
    implementation(Libraries.composeRuntimeLivedata)
    implementation(Libraries.composeNavigation)
    implementation(project(":shared"))
    implementation(Libraries.koin_android_compose)
    implementation(Libraries.koin_android)
}
