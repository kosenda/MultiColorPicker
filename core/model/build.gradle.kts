plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.room")
    id("multicolorpicker.android.library.jacoco")
    id(libs.plugins.kotlin.serialization.get().pluginId)
}

android {
    namespace = "kosenda.makecolor.core.model"
}

dependencies {
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.compose.color.picker.android)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(project(":core:testing"))
}