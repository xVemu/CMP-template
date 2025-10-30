plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.app) apply false
    alias(libs.plugins.shared.compose.hotreload) apply false
    alias(libs.plugins.shared.compose.kotlin) apply false
    alias(libs.plugins.shared.compose.compiler) apply false
    alias(libs.plugins.shared.kotlin) apply false
    alias(libs.plugins.shared.network.ktorfit) apply false
    alias(libs.plugins.shared.network.serialization) apply false
    alias(libs.plugins.shared.test.mock) apply false
    alias(libs.plugins.shared.test.coverage) apply false
    alias(libs.plugins.android.library.kotlin) apply false
    alias(libs.plugins.android.library.lint) apply false
    alias(libs.plugins.android.google.services) apply false
    alias(libs.plugins.android.firebase.crashlytics) apply false
    alias(libs.plugins.android.baseline.test) apply false
    alias(libs.plugins.android.baseline.kotlin) apply false
    alias(libs.plugins.android.baseline) apply false
    alias(libs.plugins.android.test.screenshot) apply false
}
