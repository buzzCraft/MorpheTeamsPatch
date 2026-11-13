rootProject.name = "MorpheTeamsPatch"

include("patch-source")

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        google()
        maven { url = uri("https://jitpack.io") }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/MorpheApp/registry")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: "unused"
                password = System.getenv("GITHUB_TOKEN") ?: "unused"
            }
        }
    }
}

plugins {
    id("app.morphe.patches") version "1.3.4"
}