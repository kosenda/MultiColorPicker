@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.serialization.get().pluginId)
}

android {
    compileSdk = 33
    namespace = "kosenda.makecolor.core.model"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.compose.color.picker.android)
    implementation(libs.room.runtime)
    implementation(libs.kotlinx.serialization.json)
    annotationProcessor(libs.room.compiler)
}