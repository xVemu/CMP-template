import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.baseline.test)
    alias(libs.plugins.android.baseline.kotlin)
    alias(libs.plugins.android.baseline)
}

android {
    namespace = "pl.inno4med.baselineprofile"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    defaultConfig {
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
    }

    targetProjectPath = ":composeApp"

}

kotlin {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_11)
}

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
    useConnectedDevices = true
    skipBenchmarksOnEmulator = false
}

dependencies {
    implementation(libs.android.test.junit)
    implementation(libs.android.test.ui)
    implementation(libs.android.test.benchmark)
}

androidComponents {
    onVariants { v ->
        val artifactsLoader = v.artifacts.getBuiltArtifactsLoader()
        v.instrumentationRunnerArguments.put(
            "targetAppId",
            v.testedApks.map { artifactsLoader.load(it)!!.applicationId }
        )
    }
}
