@file:Suppress("UnstableApiUsage", "UnstableApiUsage")

val ktlint: Configuration by configurations.creating

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
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
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // 参考 -> https://github.com/facebook/react-native/issues/35979#issuecomment-1405377512
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }

    // color picker
    implementation("com.godaddy.android.colorpicker:compose-color-picker:0.7.0")
    // with Android ColorInt extensions
    implementation("com.godaddy.android.colorpicker:compose-color-picker-android:0.7.0")

    // Accompanist
    implementation("com.google.accompanist:accompanist-swiperefresh:0.23.1")
    implementation("com.google.accompanist:accompanist-pager:0.23.1")

    // palette API
    implementation("androidx.palette:palette-ktx:1.0.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0-alpha02")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.activity:activity:1.6.1")

    // Compose
    implementation("androidx.compose.ui:ui:1.3.3")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation("androidx.activity:activity-compose:1.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.core:core-ktx:1.9.0")

    // AD
    implementation("com.google.android.gms:play-services-ads:21.5.0")

    // コルーチン
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // ROOM
    implementation("androidx.room:room-runtime:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")

    // Test関連
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // FireBase
    implementation(platform("com.google.firebase:firebase-bom:31.1.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.0")

    // Material3
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.compose.material3:material3:1.1.0-alpha08")

    // accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    // OSS Licenses Gradle Plugin
    implementation("com.google.android.gms:play-services-oss-licenses:17.0.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-compiler:2.45")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // dataStore preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Google Fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.3.3")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // Navigation Animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.24.5-alpha")

    // ktlint
    ktlint("com.pinterest:ktlint:0.48.0") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

// チェック
//val ktlintCheck by tasks.creating(JavaExec::class) {
tasks.create<JavaExec>("ktlintCheck") {
    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args = listOf("src/**/*.kt")
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