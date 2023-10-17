import com.mikepenz.aboutlibraries.plugin.DuplicateMode
import com.mikepenz.aboutlibraries.plugin.DuplicateRule

plugins {
    alias(libs.plugins.aboutlibraries)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.multiplatform)
    id("kotlin-parcelize") apply false
    alias(libs.plugins.serialization)
    alias(libs.plugins.moko.resources)
    alias(libs.plugins.ktorfit)
}

val artifact = "dev.datlag.vegan.shopping"
val appVersion = "1.0.0"
val appCode = 100

group = artifact
version = appVersion

kotlin {
    androidTarget {
        jvmToolchain(CompileOptions.jvmTargetVersion)
    }
    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.material3)
                api(compose.materialIconsExtended)
                api(compose.ui)
                api(compose.animation)
                api(compose.animationGraphics)

                api(libs.stdlib)

                api(libs.decompose)
                api(libs.coroutines)
                api(libs.kodein)
                api(libs.kodein.compose)

                implementation(libs.aboutlibraries)
                implementation(libs.aboutlibraries.compose)

                api(libs.kamel)
                api(libs.napier)
                api(libs.moko.resources.compose)

                api(libs.windowsize.multiplatform)
                api(libs.insetsx)

                api(libs.ktor)
                api(libs.ktor.content.negotiation)
                api(libs.ktor.serialization.json)

                api(project(":model"))
                api(project(":network"))
            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            apply(plugin = "kotlin-parcelize")

            dependencies {
                implementation(libs.activity)
                implementation(libs.activity.compose)
                implementation(libs.android)
                implementation(libs.appcompat)
                implementation(libs.coroutines.android)
                implementation(libs.material)
                implementation(libs.multidex)
                implementation(libs.splashscreen)
                implementation(libs.accompanist.permission)
                implementation(libs.decompose.compose)

                implementation(libs.mlkit.barcode)
                implementation(libs.mlkit.language)
                implementation(libs.mlkit.translate)

                implementation(libs.camerax)
                implementation(libs.camerax.lifecycle)
                implementation(libs.camerax.mlkit)
                implementation(libs.camerax.view)

                implementation(libs.ktor.android)
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
        }
    }
}

android {
    sourceSets["main"].setRoot("src/androidMain/")
    sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    sourceSets["main"].assets.srcDirs("src/androidMain/assets", "src/commonMain/assets")

    compileSdk = Configuration.compileSdk
    buildToolsVersion = Configuration.buildTools
    namespace = artifact

    defaultConfig {
        applicationId = artifact
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = appCode
        versionName = appVersion

        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = CompileOptions.sourceCompatibility
        targetCompatibility = CompileOptions.targetCompatibility
    }

    packaging {
        resources.merges.add("META-INF/LICENSE")
        resources.merges.add("META-INF/DEPENDENCIES")
        resources.pickFirsts.add("**")
        resources.pickFirsts.add("*")
    }

    buildFeatures {
        buildConfig = true
    }
}

multiplatformResources {
    multiplatformResourcesPackage = artifact
    multiplatformResourcesClassName = "SharedRes"
}

aboutLibraries {
    includePlatform = true
    duplicationMode = DuplicateMode.MERGE
    duplicationRule = DuplicateRule.GROUP
    excludeFields = arrayOf("generated")
}