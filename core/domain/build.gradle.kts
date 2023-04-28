plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.room")
    id("multicolorpicker.android.hilt")
    id("multicolorpicker.android.library.jacoco")
}

android {
    namespace = "kosenda.makecolor.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:mock"))
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.palette)
    implementation(libs.compose.color.picker.android)
    implementation(libs.timber)
    testImplementation(project(":core:testing"))
}