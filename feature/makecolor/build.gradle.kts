plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.library.compose")
    id("multicolorpicker.android.library.jacoco")
    id("multicolorpicker.android.hilt")
}

android {
    namespace = "kosenda.makecolor.feature.makecolor"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":feature:preview"))
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.palette)
    implementation(libs.compose.color.picker)
    implementation(libs.timber)
    testImplementation(project(":core:testing"))
}