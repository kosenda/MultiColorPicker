plugins {
    id("multicolorpicker.android.library")
    id(libs.plugins.kotlin.serialization.get().pluginId)
}

android {
    namespace = "kosenda.makecolor.core.ui"
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
    implementation(project(":core:util"))
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.ui.google.fonts)
    implementation(libs.compose.color.picker)
    implementation(libs.kotlinx.serialization.json)
}