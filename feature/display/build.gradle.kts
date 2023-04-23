plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.library.jacoco")
    id("multicolorpicker.android.hilt")
    id("multicolorpicker.android.library.compose")
}

android {
    namespace = "kosenda.makecolor.feature.display"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":feature:edit"))
    implementation(project(":feature:preview"))
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(project(":core:testing"))
}