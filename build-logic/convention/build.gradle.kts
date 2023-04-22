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
        register("androidApplicationJacoco") {
            id = "multicolorpicker.android.application.jacoco"
            implementationClass = "kosenda.makecolor.AndroidApplicationJacocoPlugin"
        }
        register("androidApplication") {
            id = "multicolorpicker.android.application"
            implementationClass = "kosenda.makecolor.AndroidApplicationPlugin"
        }
        register("androidLibraryJacoco") {
            id = "multicolorpicker.android.library.jacoco"
            implementationClass = "kosenda.makecolor.AndroidLibraryJacocoPlugin"
        }
        register("androidLibraryCompose") {
            id = "multicolorpicker.android.library.compose"
            implementationClass = "kosenda.makecolor.AndroidLibraryComposePlugin"
        }
        register("androidLibrary") {
            id = "multicolorpicker.android.library"
            implementationClass = "kosenda.makecolor.AndroidLibraryPlugin"
        }
        register("androidHilt") {
            id = "multicolorpicker.android.hilt"
            implementationClass = "kosenda.makecolor.AndroidHiltPlugin"
        }
        register("androidRoom") {
            id = "multicolorpicker.android.room"
            implementationClass = "kosenda.makecolor.AndroidRoomPlugin"
        }
    }
}