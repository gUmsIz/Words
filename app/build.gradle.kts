plugins {
    id(BuildPlugins.androidApplication)
    kotlin(BuildPlugins.kotlinAndroid)
}

android {
    namespace = "com.gumsiz.words"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.gumsiz.words"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildPlugins.Versions.composeCompilerVersin
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.ktxCore)
    testImplementation(TestLibraries.junit4)
    androidTestImplementation(TestLibraries.junit)
    androidTestImplementation(TestLibraries.espresso)

    implementation(Libraries.composeActivity)
    implementation(Libraries.composeAnimation)
    implementation(Libraries.composeMaterial)
    implementation(Libraries.composeUi)
    implementation(Libraries.composeUiToolingPrev)
    debugImplementation(Libraries.composeUiTooling)
    implementation(Libraries.composeViewmodel)
    implementation(Libraries.composeRuntime)
    implementation(Libraries.composeRuntimeLivedata)
    implementation(Libraries.composeNavigation)
    implementation(Libraries.koin_android_compose)
    implementation(Libraries.koin_android)
}