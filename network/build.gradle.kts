plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.library)
    alias(libs.plugins.ktorfit) apply false
}

val artifact = "dev.datlag.vegan.shopping.network"
group = artifact

kotlin {
    androidTarget()

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
        val commonTest by getting {
            apply(plugin = libs.plugins.ktorfit.get().pluginId)
            dependencies {
                implementation(libs.ktor.cio)
                implementation(libs.test)
                implementation(libs.turbine)
                implementation(libs.ktor.content.negotiation)
                implementation(libs.ktor.serialization.json)
                implementation(libs.coroutines.test)

                implementation(libs.ktorfit)
                implementation(project(":model"))
            }
        }

        val androidMain by getting {
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
