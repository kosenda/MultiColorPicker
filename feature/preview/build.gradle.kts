plugins {
    id("multicolorpicker.android.library")
}

android {
    namespace = "kosenda.makecolor.feature.preview"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
}