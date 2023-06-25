plugins{
    `kotlin-dsl`
}

repositories{
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies{
    implementation(lib.android.gradlePlugin)
    implementation(lib.jetbrains.kotlin.gradlePlugin)
    implementation(lib.javaPoet)
}