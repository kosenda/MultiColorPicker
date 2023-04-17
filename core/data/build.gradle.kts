plugins {
    id("multicolorpicker.android.library")
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
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
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.timber)
    kapt(libs.hilt.compiler)
    ksp(libs.room.compiler)
}