plugins {
    id("multicolorpicker.android.library")
}

android {
    namespace = "kosenda.makecolor.core.resource"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)
}