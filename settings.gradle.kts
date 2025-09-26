rootProject.name = "Asystent"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://proxyrepo.inno4med.pl/repository/maven-proxy-grouped/")
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://proxyrepo.inno4med.pl/repository/maven-proxy-grouped/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")
