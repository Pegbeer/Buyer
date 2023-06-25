rootProject.name = "Buyer"
include(
    ":app",
    ":domain",
    ":infrastructure"
)
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":infrastructure")
