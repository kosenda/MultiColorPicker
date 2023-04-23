plugins {
    id("multicolorpicker.android.library")
}

android {
    namespace = "kosenda.makecolor.core.testing"
}

dependencies {
    api(libs.androidx.coroutines.test)
    api(libs.junit)
    api(libs.truth)
}