plugins {
    kotlin(BuildPlugins.multiplatform)
    id(BuildPlugins.androidLibrary)
    kotlin(BuildPlugins.serialization) version kotlinVersion
    id(BuildPlugins.realm) version realmKotlinVersion
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
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
        val commonTest by getting {
            dependencies {
                //implementation(libs.kotlin.test)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Libraries.ktor_client_android)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting{
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Libraries.ktor_client_native)
            }
        }
    }
}

android {
    namespace = "com.gumsiz.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
