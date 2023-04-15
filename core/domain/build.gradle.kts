plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    compileSdk = 33
    namespace = "kosenda.makecolor.core.domain"
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.palette)
    implementation(libs.hilt.android)
    implementation(libs.compose.color.picker.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.room.runtime)
    implementation(libs.timber)
    kapt(libs.hilt.compiler)
    ksp(libs.room.compiler)
}