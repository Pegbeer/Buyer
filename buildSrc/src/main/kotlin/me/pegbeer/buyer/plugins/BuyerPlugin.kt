package me.pegbeer.buyer.plugins

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Locale


class BuyerPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.apply<Project>{
            plugins.all{
                when(this){
                    is LibraryPlugin -> configureLibraryPlugin()
                    is AppPlugin -> configureAppPlugin()
                }
            }
        }
    }

    private fun Project.configureLibraryPlugin() {
        extensions.getByType<LibraryExtension>().apply {
            configureCommonOptions(this@configureLibraryPlugin)
        }
    }

    private fun Project.configureAppPlugin() {
        extensions.getByType<BaseAppModuleExtension>().apply {
            configureCommonOptions(this@configureAppPlugin)

            defaultConfig {
                applicationId = AndroidConfig.APPLICATION_ID
                versionCode = AndroidConfig.VERSION_CODE
                versionName = AndroidConfig.VERSION_NAME
            }

            buildTypes {
                getByName("debug") {
                    applicationIdSuffix = ".debug"
                    versionNameSuffix = "-debug"
                }
                getByName("release") {
                    isShrinkResources = true
                }
            }

            flavorDimensions += "releaseType"
            productFlavors {
                register("base") {
                    dimension = "releaseType"
                }
                register("play") {
                    dimension = "releaseType"
                }
            }

            (sourceSets) {
                "base" {
                    java {
                        srcDir("src/base/kotlin")
                    }
                }
                "play" {
                    java {
                        srcDir("src/play/kotlin")
                    }
                }
            }
        }
    }

    private fun BaseExtension.configureCommonOptions(project: Project) {
        compileSdkVersion(AndroidConfig.COMPILE_SDK)

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        defaultConfig {
            minSdk = AndroidConfig.MIN_SDK
            targetSdk = AndroidConfig.TARGET_SDK
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables.useSupportLibrary = true
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            }
        }

        @Suppress("deprecation") // Move to CommonExtension
        lintOptions {
            isAbortOnError = false
        }

        packagingOptions {
            resources.excludes += setOf(
                "META-INF/AL2.0",
                "META-INF/LGPL2.1"
            )
        }

        testOptions {
            unitTests.isIncludeAndroidResources = true
        }

        project.tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                freeCompilerArgs += listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                )
            }
        }
    }

}