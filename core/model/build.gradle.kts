plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.serialization.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    compileSdk = 33
    namespace = "kosenda.makecolor.core.model"
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.compose.color.picker.android)
    implementation(libs.room.runtime)
    implementation(libs.kotlinx.serialization.json)
    ksp(libs.room.compiler)
}