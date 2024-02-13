import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.multiplatformResources)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.serialization)
    alias(libs.plugins.parcelize)
    id("kotlin-parcelize")
}

buildConfig {
    buildConfigField("VERSION_NAME", "1.0.11")
    buildConfigField("VERSION_CODE", 11)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

//    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
        dependencies {
            api("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
            api("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2-compose-experimental")
            api("com.arkivanov.essenty:lifecycle:1.3.0")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(libs.kotlinx.datetime)

                implementation(libs.multiplatform.resources)
                implementation(libs.multiplatform.resources.compose)
                implementation(libs.decompose)
                api("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2-compose-experimental")
//            implementation(libs.androidx.lifecycle.viewmodel.ktx)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.me.multiplatform"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].java.srcDirs("build/generated/moko/androidMain/src")

    defaultConfig {
        applicationId = "com.me.multiplatform"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures { compose = true }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        implementation(libs.androidx.ui.tooling.preview.android)
        api(compose.preview)
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.me.resources.library"
    multiplatformResourcesClassName = "MR"
    disableStaticFrameworkWarning = true
}
