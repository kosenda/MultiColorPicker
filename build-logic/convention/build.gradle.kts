plugins {
    `kotlin-dsl`
}

group = "kosenda.makecolor.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "multicolorpicker.android.application"
            implementationClass = "kosenda.makecolor.AndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "multicolorpicker.android.library"
            implementationClass = "kosenda.makecolor.AndroidLibraryPlugin"
        }
    }
}