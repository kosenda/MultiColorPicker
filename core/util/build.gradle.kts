plugins {
    id("multicolorpicker.android.library")
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
    namespace = "kosenda.makecolor.core.util"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.color.picker)
    implementation(libs.hilt.android)
    testImplementation(libs.junit)
    testImplementation(libs.truth)
    kapt(libs.hilt.compiler)
}