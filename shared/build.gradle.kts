plugins {
    kotlin(BuildPlugins.multiplatform)
    id(BuildPlugins.androidLibrary)
}

//@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    //targetHierarchy.default()

    /*androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }*/
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                //TODO move to Dependencies.kt
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.gumsiz.shared"
    compileSdk = AndroidSdk.compileSdk
    defaultConfig {
        minSdk = AndroidSdk.minSdk
    }
}
