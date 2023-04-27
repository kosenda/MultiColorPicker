buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    jacoco
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.oss.licenses) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.create<JacocoReport>("mergeJacoco") {
    gradle.afterProject {
        if (project.rootProject != project &&
            (project.plugins.hasPlugin("multicolorpicker.android.application.jacoco") ||
                    project.plugins.hasPlugin("multicolorpicker.android.library.jacoco")
            )
        ) {
            executionData.from.add(fileTree("${project.buildDir}/jacoco"))
            sourceDirectories.from.add(files("$projectDir/src/main/java", "$projectDir/src/main/kotlin"))
            classDirectories.from.add(
                fileTree("${project.buildDir}/tmp/kotlin-classes/debug") {
                    exclude(coverageExclusions)
                },
            )
        }
    }
    reports {
        html.required.set(true)
        xml.required.set(true)
    }
}

private val coverageExclusions = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/*Fake*.*",
    "**/*Preview*.*",
    "**/BuildConfig.*",
    "**/*Manifest*.*",
    "**/*Test*.*",
    "**/*Hilt*.*",
    "**/*Factory*.*",
    "**/*Module*.*",
    "**/*Key*.*",
    "**/*Screen*.*",
    "**/*Content*.*",
    "**/*Dialog*.*",
    "**/*Drawer*.*",
    "**/*GoogleAd*.*",
    "**/*MainActivity*.*",
    "**/ui/feature/common/**",
)