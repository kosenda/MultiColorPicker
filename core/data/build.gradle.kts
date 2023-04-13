plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
}

android {
    compileSdk = 33
    namespace = "kosenda.makecolor.core.data"
    defaultConfig {
        minSdk = 24
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.timber)
    kapt(libs.hilt.compiler)
    ksp(libs.room.compiler)
}