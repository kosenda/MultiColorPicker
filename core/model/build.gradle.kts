@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.android.library.get().pluginId) apply true
    id(libs.plugins.kotlin.android.get().pluginId) apply true
    alias(libs.plugins.kotlin.serialization) apply true
}

android {
    compileSdk = 33
    namespace = "kosenda.makecolor.core.model"
}

dependencies {
    implementation(libs.compose.color.picker.android)
    implementation(libs.room.runtime)
    implementation(libs.kotlinx.serialization.json)
    annotationProcessor(libs.room.compiler)
}