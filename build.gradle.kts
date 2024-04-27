plugins {
    id(BuildPlugins.androidApplication) version BuildPlugins.Versions.buildToolsVersion apply false
    id(BuildPlugins.androidLibrary) version BuildPlugins.Versions.buildToolsVersion apply false
    kotlin(BuildPlugins.kotlinAndroid) version kotlinVersion apply false
    kotlin(BuildPlugins.multiplatform) version kotlinVersion apply false
}
