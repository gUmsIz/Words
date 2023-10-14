plugins {
    kotlin(BuildPlugins.multiplatform)
    kotlin(BuildPlugins.serialization) version kotlinVersion
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.realm) version realmKotlinVersion
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    androidTarget()
    
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
                implementation(Libraries.ktor_client_core)
                implementation(Libraries.ktor_client_cont)
                implementation(Libraries.ktor_client_json)
                implementation(Libraries.realm_kotlin_base)
                implementation(Libraries.coroutine_core)
                implementation (Libraries.serialization)
                api(Libraries.koin_core)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libraries.ktor_client_android)
            }
        }
        val commonTest by getting {
            dependencies {
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
