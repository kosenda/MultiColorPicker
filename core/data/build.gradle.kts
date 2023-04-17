plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.room")
    id("multicolorpicker.android.hilt")
}

android {
    namespace = "kosenda.makecolor.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
}