plugins {
    id("multicolorpicker.android.library")
    id(libs.plugins.kotlin.serialization.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "kosenda.makecolor.core.model"
}

dependencies {
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.compose.color.picker.android)
    implementation(libs.room.runtime)
    implementation(libs.kotlinx.serialization.json)
    ksp(libs.room.compiler)
}