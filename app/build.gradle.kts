plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.kotlinNavigationSafeargs)
}


android {
    compileSdk = AndroidSdk.compileSdk

    defaultConfig {
        applicationId = "com.gumsiz.words"
        minSdk = AndroidSdk.minSdk
        targetSdk = AndroidSdk.targetSdk
        versionCode = 1
        versionName = "1.0"
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
        kotlinCompilerExtensionVersion = "1.2.0-alpha02"
    }
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

    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomKtx)
    kapt(Libraries.roomCompiler)

    // Retrofit for downloading data
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGson)

    implementation(Libraries.material)

    implementation(Libraries.composeActivity)
    implementation(Libraries.composeAnimation)
    implementation(Libraries.composeMaterial)
    implementation(Libraries.composeUi)
    implementation(Libraries.composeViewmodel)
    implementation(Libraries.composeRuntime)
    implementation(Libraries.composeRuntimeLivedata)
    implementation(Libraries.composeNavigation)
//    implementation(Libraries.composeIcons)
}
