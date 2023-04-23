plugins {
    id("multicolorpicker.android.library")
}

android {
    namespace = "kosenda.makecolor.core.testing"
}

dependencies {
    api(libs.androidx.test)
    api(libs.androidx.test.core)
    api(libs.androidx.coroutines.test)
    api(libs.junit)
    api(libs.robolectric)
    api(libs.truth)
}