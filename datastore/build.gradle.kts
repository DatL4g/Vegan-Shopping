plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
}

val artifact = "dev.datlag.vegan.shopping.datastore"
group = artifact

kotlin {
    sourceSets {
        androidTarget()

        val commonMain by getting {
            dependencies {
                api(libs.datastore)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.datastore.android)
            }
        }
    }
}

android {
    compileSdk = Configuration.compileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    namespace = artifact

    defaultConfig {
        minSdk = Configuration.minSdk
    }

    compileOptions {
        sourceCompatibility = CompileOptions.sourceCompatibility
        targetCompatibility = CompileOptions.targetCompatibility
    }
}