plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.android.library)
    id ("kotlin-parcelize") apply false
}

val artifact = "dev.datlag.vegan.shopping.model"
group = artifact

kotlin {
    jvm()
    androidTarget()
    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
            	api(libs.parcelable)
                api(libs.serialization.json)
                api(libs.coroutines)
            }
        }

        val jvmMain by getting {
            dependsOn(commonMain)
        }
        
        val androidMain by getting {
            dependsOn(jvmMain)
            apply(plugin = "kotlin-parcelize")
        }

        val iosMain by getting {
            dependsOn(commonMain)
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

