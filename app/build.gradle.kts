import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    `kotlin-android`
    `kotlin-kapt`
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
    BuyerPlugin
}

/*val signPropertiesFile = rootProject.file("sign.properties")
val signProperties = Properties()
signProperties.load(FileInputStream(signPropertiesFile))*/


android {
    namespace = "com.pegbeer.buyer"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.pegbeer.buyer"

        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        resValue("integer", "version_code", "${AndroidConfig.VERSION_CODE}")
    }

    buildFeatures {
        viewBinding = true
    }

    /*signingConfigs{
        create("release"){
            keyAlias = signProperties["keyAlias"].toString()
            keyPassword = signProperties["keyPassword"].toString()
            storeFile = file(signProperties["storeFile"].toString())
            storePassword = signProperties["storePassword"].toString()
        }
    }*/

    buildTypes{
        getByName("debug"){
            isDebuggable = true
            isMinifyEnabled = false
        }

        getByName("release"){
            isMinifyEnabled = true
            isDebuggable = false
            /*signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")*/
        }
    }
}

dependencies {

    implementation(project(":infrastructure"))
    implementation(project(":domain"))

    // kotlin
    implementation(libs.jetbrains.kotlinx.coroutines.core)
    implementation(libs.jetbrains.kotlinx.coroutines.android)

    kapt(libs.androidx.hilt.compiler)

    // androidx
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.google.material)

    //appcenter
    implementation(libs.appcenter.analytics)
    implementation(libs.appcenter.crashes)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // dagger
    kapt(libs.google.dagger.hilt.android.compiler)
    implementation(libs.google.dagger.hilt.android)

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    androidTestImplementation(libs.google.dagger.hilt.android.testing)
    kaptAndroidTest(libs.google.dagger.hilt.compiler)


    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.runner)


}