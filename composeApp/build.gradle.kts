import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.app)
    alias(libs.plugins.android.google.services) // in app update, review
    alias(libs.plugins.android.firebase.crashlytics)
    alias(libs.plugins.android.baseline)

    alias(libs.plugins.shared.kotlin)
    alias(libs.plugins.shared.compose.kotlin)
    alias(libs.plugins.shared.compose.compiler)
    alias(libs.plugins.shared.compose.hotreload)
    alias(libs.plugins.shared.ksp)
    alias(libs.plugins.shared.network.ktorfit)
    alias(libs.plugins.shared.network.serialization)
    alias(libs.plugins.shared.db.room)
}

kotlin {
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true

            export(libs.shared.firebase.messaging)
            export(libs.ios.firebase.crashlytics)
        }
    }

    jvm()

    sourceSets.named("commonMain").configure {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.android.compose)
            implementation(libs.android.utils.appupdate)
            implementation(libs.android.splashscreen)
            implementation(libs.android.firebase.crashlytics)
            implementation(libs.android.baseline.installer)
            implementation(libs.android.di.startup)
        }
        androidUnitTest.dependencies {
            implementation(libs.android.test.mock)
            implementation(libs.android.test.mock.runtime)
            implementation(libs.android.test.runtime)
        }
        commonMain.dependencies {
            implementation(projects.components)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.shared.navigation.compose)

            implementation(libs.shared.compose.viewmodel)
            implementation(libs.shared.compose.lifecycle)

            implementation(libs.shared.di.compose)
            implementation(libs.shared.di.viewmodel)
            implementation(libs.shared.di.navigation)
            api(libs.shared.di.annotations)

            implementation(libs.shared.db.runtime)
            implementation(libs.shared.db.sql)
            implementation(libs.shared.db.datastore)

            implementation(libs.shared.network.ktorfit)
            implementation(ktorLibs.client.core) // TODO alias to libs.shared.network.ktor.client
            implementation(ktorLibs.client.contentNegotiation)
            implementation(ktorLibs.serialization.kotlinx.json)

            implementation(libs.shared.coil)
            implementation(libs.shared.coil.ktor)
            implementation(libs.shared.coil.svg)

            api(libs.shared.firebase.messaging)

            implementation(libs.shared.utils.logging)
            implementation(libs.shared.utils.uri)
            implementation(libs.shared.utils.kmpessentials)
            implementation(libs.shared.utils.datetime)
        }
        commonTest.dependencies {
            implementation(libs.shared.test)
            implementation(libs.shared.test.assertions)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.jvm.utils.coroutines)
            implementation(libs.jvm.utils.datetime)
        }
        jvmTest.dependencies {
            implementation(libs.jvm.test.mock)
        }
        iosMain.dependencies {
            api(libs.ios.firebase.crashlytics)
        }
    }
}

compose.desktop {
    application {
        mainClass = "pl.inno4med.asystent.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "pl.inno4med.asystent"
            packageVersion = "1.0.0"
        }
        buildTypes.release.proguard {
            isEnabled = false
        }
    }
}

android {
    namespace = "pl.inno4med.asystent"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "pl.inno4med.asystent"
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    signingConfigs {
        create("release") {
            val keystore =
                file("key.properties").inputStream()
                    .use { Properties().apply { load(it) } }

            storeFile = file(keystore["storeFile"] as String)
            storePassword = keystore["storePassword"] as String
            keyAlias = keystore["keyAlias"] as String
            keyPassword = keystore["keyPassword"] as String
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["crashlyticsCollectionEnabled"] = true
        }

        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            manifestPlaceholders["crashlyticsCollectionEnabled"] = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    "baselineProfile"(projects.baselineprofile)
    // TODO https://youtrack.jetbrains.com/issue/KMT-1312/Preview-not-work-in-commonMain-with-multi-module
    debugImplementation(compose.uiTooling)
    add("kspCommonMainMetadata", libs.shared.di.compiler)

    listOf("kspAndroid", "kspIosArm64", "kspIosSimulatorArm64", "kspJvm").forEach {
        add(it, libs.shared.di.compiler)
        add(it, libs.shared.db.compiler)
    }
}

// TODO https://github.com/google/ksp/issues/2442 https://github.com/InsertKoinIO/koin/issues/2174
tasks.matching { it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata" }
    .configureEach {
        dependsOn("kspCommonMainKotlinMetadata")
    }

afterEvaluate {
    val list = listOf(
        "Debug",
        "Release",
        "NonMinifiedRelease",
        "BenchmarkRelease"
    )

    list.forEach { taskName ->
        tasks.named("ksp${taskName}KotlinAndroid") {
            dependsOn("generateResourceAccessorsForAndroid${taskName}")
            dependsOn("generateResourceAccessorsForAndroidMain")
            dependsOn("generateActualResourceCollectorsForAndroidMain")
        }
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
    arg("KOIN_USE_COMPOSE_VIEWMODEL", "true")
}

room {
    schemaDirectory("$projectDir/schemas")
}

baselineProfile {
    automaticGenerationDuringBuild = true
    dexLayoutOptimization = true
    saveInSrc = false
}
