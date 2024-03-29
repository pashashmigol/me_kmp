import org.jetbrains.compose.ExperimentalComposeLibrary
import java.util.regex.Pattern

val appId = "com.me.diary"
val versionName = "1.0.11"
val versionCode = 11

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.multiplatformResources)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.serialization)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.baselineprofile)
}

buildConfig {
    packageName("com.me.diary")
    buildConfigField("VERSION_NAME", versionName)
    buildConfigField("VERSION_CODE", 11)
    buildConfigField("PACKAGE_NAME", appId)
    useKotlinOutput { internalVisibility = false }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
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
            api(libs.decompose)
            api(libs.arkivanov.extensions)
            api(libs.lifecycle)
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
            }
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
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.okio)
                implementation(libs.kodein.di)
                implementation(libs.kotlinx.coroutines.core)

                api(libs.arkivanov.extensions)
                api(libs.kmm.viewmodel.core)
            }
        }
        val androidUnitTest by getting {
            dependsOn(androidMain)
            dependsOn(commonMain)
            dependencies {
                implementation(libs.junit)
                implementation(libs.kotlin.test)
                implementation(libs.androidx.ui.test.junit4.android)
                implementation(libs.robolectric)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
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
    namespace = appId
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].java.srcDirs("build/generated/moko/androidMain/src")

    defaultConfig {
        applicationId = appId
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionCode
        versionName = versionName
        testInstrumentationRunner = "org.robolectric.RobolectricTestRunner"
        missingDimensionStrategy("version", "demo")
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            all { test ->
                if(project.hasProperty("skipUI")) {
                    test.exclude("**/screens/**")
                }
            }
        }
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
        implementation(libs.androidx.metrics.performance)
        implementation(libs.androidx.profileinstaller)
        "baselineProfile"(project(":baselineprofile"))
    }
    buildConfig.buildConfigField(
        "GENERATE_TEST_RECORDS",
        shouldGenerateTestRecords()
    )
}

fun shouldGenerateTestRecords(): Boolean {
    gradle.startParameter.taskRequests.forEach {  it: TaskExecutionRequest ->
        println("### ${it}")
        println("### ${it.args.joinToString()}")
    }
    val taskRequestsStr = gradle.startParameter.taskRequests.toString()
    val pattern: Pattern = if (taskRequestsStr.contains("assemble")) {
        Pattern.compile("assemble(\\w+)(Release|Debug)")
    } else {
        Pattern.compile("bundle(\\w+)(Release|Debug)")
    }

    val matcher = pattern.matcher(taskRequestsStr)
    val flavor = if (matcher.find()) {
        matcher.group(1).lowercase()
    } else {
        println("### NO FLAVOR FOUND")
        ""
    }
    println("### flavor = $flavor")
    return flavor.contains("nonminified")
}

multiplatformResources {
    multiplatformResourcesPackage = "com.me.resources.library"
    multiplatformResourcesClassName = "MR"
    disableStaticFrameworkWarning = true
}
