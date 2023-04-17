plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.library.compose")
}

android {
    namespace = "kosenda.makecolor.feature.preview"
}

dependencies {
    implementation(project(":core:ui"))
}