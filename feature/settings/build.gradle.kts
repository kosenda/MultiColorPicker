plugins {
    id("multicolorpicker.android.library")
    id("multicolorpicker.android.hilt")
}

android {
    namespace = "kosenda.makecolor.feature.settings"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":feature:preview"))
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.timber)
}