plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.room")
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "kosenda.makecolor.core.domain"
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
    implementation(libs.timber)
    kapt(libs.hilt.compiler)
}