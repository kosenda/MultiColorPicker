@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.google.android.gms.oss-licenses-plugin" -> useModule("com.google.android.gms:oss-licenses-plugin:${requested.version}")
            }
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
include(":app")
include(":core:data")
include(":core:domain")
include(":core:mock")
include(":core:model")
include(":core:resource")
include(":core:testing")
include(":core:ui")
include(":core:util")
include(":feature:display")
include(":feature:edit")
include(":feature:info")
include(":feature:makecolor")
include(":feature:preview")
include(":feature:settings")
rootProject.name = "multicolorpicker"
