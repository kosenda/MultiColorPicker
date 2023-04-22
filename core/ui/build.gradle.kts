plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.library.compose")
    id("multicolorpicker.android.library.jacoco")
    id(libs.plugins.kotlin.serialization.get().pluginId)
}

android {
    namespace = "kosenda.makecolor.core.ui"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(project(":core:util"))
    implementation(libs.androidx.ui.google.fonts)
    implementation(libs.compose.color.picker)
    implementation(libs.kotlinx.serialization.json)
}