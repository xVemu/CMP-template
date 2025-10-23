import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    alias(libs.plugins.shared.kotlin)
    alias(libs.plugins.shared.compose.kotlin)
    alias(libs.plugins.shared.compose.compiler)
    alias(libs.plugins.shared.compose.hotreload)
    alias(libs.plugins.android.library.kotlin)
    alias(libs.plugins.android.library.lint)
}

kotlin {
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")
    explicitApi()

    androidLibrary {
        namespace = "pl.inno4med.components"
        compileSdk = libs.versions.android.sdk.compile.get().toInt()
        minSdk = libs.versions.android.sdk.min.get().toInt()

        androidResources.enable = true
    }

    jvm()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "componentsKit"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.components.resources)

            implementation(libs.shared.utils.kmpessentials)

            implementation(libs.shared.navigation.compose)

            implementation(libs.shared.compose.adaptive) //  currentWindowAdaptiveInfo()
            implementation(compose.material3AdaptiveNavigationSuite) // NavigationSuiteScaffoldLayout, AdaptiveLayout
//            implementation(libs.material3.adaptive.layout) // ListDetailPaneScaffold, SupportingPaneScaffold TODO Integrate with compose-navigation https://issuetracker.google.com/issues/294612000
//            implementation(libs.material3.adaptive.navigation) // rememberListDetailPaneScaffoldNavigator, rememberSupportingPaneScaffoldNavigator
        }

        commonTest.dependencies {
            implementation(libs.shared.test)
            implementation(libs.shared.test.assertions)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        jvmTest.dependencies {
            implementation(compose.desktop.currentOs)
        }

        iosMain.dependencies {
        }
    }
}
