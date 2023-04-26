plugins {
    id("multicolorpicker.android.library")
}

android {
    namespace = "kosenda.makecolor.core.mock"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:util"))
}