plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.library.jacoco")
    id("multicolorpicker.android.hilt")
}

android {
    namespace = "kosenda.makecolor.core.util"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.color.picker)
    testImplementation(project(":core:testing"))
    testImplementation(project(":core:util"))
}