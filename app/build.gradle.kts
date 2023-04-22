@file:Suppress("UnstableApiUsage")

val ktlint: Configuration by configurations.creating

plugins {
    id("multicolorpicker.android.application")
    id("multicolorpicker.android.application.jacoco")
    id("multicolorpicker.android.hilt")
    alias(libs.plugins.firebase.crashlytics) apply true
    alias(libs.plugins.oss.licenses) apply true
    alias(libs.plugins.gms) apply true
    alias(libs.plugins.kotlin.serialization) apply true
    alias(libs.plugins.secrets) apply true
}

android {
    namespace = "kosenda.makecolor.app"
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        release {
            isShrinkResources = true // リソースの圧縮
            isMinifyEnabled = true // コードの圧縮
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro",
                "shrinker-rules.pro",
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    lint {
        baseline = file("lint-baseline.xml")
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:resource"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":feature:display"))
    implementation(project(":feature:edit"))
    implementation(project(":feature:info"))
    implementation(project(":feature:makecolor"))
    implementation(project(":feature:preview"))
    implementation(project(":feature:settings"))
    compileOnly(libs.firebase.crashlytics.gradle)
    implementation(libs.accompanist.navigationAnimation)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.coroutines.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.google.fonts)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.play.services.ads)
    implementation(libs.oss.licenses)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.firebase.bom))
    testImplementation(libs.androidx.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.truth)
    androidTestImplementation(libs.androidx.test)

    ktlint(libs.ktlint) {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

// チェック
tasks.create<JavaExec>("ktlintCheck") {
    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf(
        "src/**/*.kt",
        "--reporter=checkstyle,output=${buildDir}/reports/ktlint/ktlint-result.xml",
    )
    isIgnoreExitValue = true
}

// フォーマット
// jvmArgsは以下のサイトを参考にした（入れないと失敗してしまう）
// https://github.com/pinterest/ktlint/issues/1195#issuecomment-1009027802
tasks.create<JavaExec>("ktlintFormatting") {
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args("-F", "src/**/*.kt")
    jvmArgs("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}