plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.library)
}

val artifact = "dev.datlag.vegan.shopping.network"
group = artifact

kotlin {
    androidTarget()
    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.serialization.json)
                api(libs.coroutines)
                api(libs.flowredux)

                api(libs.ktorfit)
                api(project(":model"))
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
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

dependencies {
    val ktorfit = libs.versions.ktorfit.get()

    add("kspCommonMainMetadata", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfit")
    add("kspAndroid", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfit")
}
