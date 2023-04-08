plugins {
    id(libs.plugins.android.library.get().pluginId)
}

android {
    compileSdk = 33
    namespace = "kosenda.makecolor.core.resource"
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}