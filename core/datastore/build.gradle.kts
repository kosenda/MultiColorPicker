plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
    compileSdk = 33
    namespace = "kosenda.makecolor.core.datastore"
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    api(libs.androidx.dataStore.preferences)
    implementation(libs.hilt.android)
    implementation(libs.timber)
    implementation(libs.kotlinx.serialization.json)
    kapt(libs.hilt.compiler)
}