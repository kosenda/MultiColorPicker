plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.room")
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
    namespace = "kosenda.makecolor.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
    kapt(libs.hilt.compiler)
}