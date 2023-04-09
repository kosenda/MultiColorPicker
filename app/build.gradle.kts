@file:Suppress("UnstableApiUsage")

val ktlint: Configuration by configurations.creating

plugins {
    alias(libs.plugins.android.application) apply true
    alias(libs.plugins.firebase.crashlytics) apply true
    alias(libs.plugins.oss.licenses) apply true
    alias(libs.plugins.gms) apply true
    alias(libs.plugins.hilt) apply true
    alias(libs.plugins.kotlin.android) apply true
    alias(libs.plugins.kotlin.kapt) apply true
    alias(libs.plugins.kotlin.serialization) apply true
    alias(libs.plugins.ksp) apply true
    alias(libs.plugins.secrets) apply true
}

android {
    compileSdk = 33

    bundle {
        language {
            enableSplit = false
        }
        storeArchive {
            enable = false
        }
    }

    defaultConfig {
        applicationId = "kosenda.makecolor"
        minSdk = 24
        targetSdk = 33
        versionCode = 35
        versionName = "4.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }
    packagingOptions {
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
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
    implementation(project(":core:domain"))
    implementation(project(":core:resource"))
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:preview"))
    compileOnly(libs.firebase.crashlytics.gradle)
    implementation(libs.accompanist.navigationAnimation)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.coroutines.android)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.palette)
    implementation(libs.androidx.ui.google.fonts)
    implementation(libs.compose.color.picker)
    implementation(libs.compose.color.picker.android)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.oss.licenses)
    implementation(libs.play.services.ads)
    implementation(libs.room.runtime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)
    implementation(platform(libs.firebase.bom))
    testImplementation(libs.junit)
    testImplementation(libs.truth)
    androidTestImplementation(libs.androidx.test)
    kapt(libs.hilt.compiler)
    ksp(libs.room.compiler)

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