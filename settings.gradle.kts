rootProject.name = "Vegan Shopping"

include("app")
include("model")
include("network")
include("datastore", "datastore:proto")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}
