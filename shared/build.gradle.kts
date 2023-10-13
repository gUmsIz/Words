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
                implementation(Libraries.realm_kotlin_base)
                implementation(Libraries.coroutine_core)
                implementation (Libraries.serialization)
                api(Libraries.koin_core)
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
