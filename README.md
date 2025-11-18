Ultimate template for creating new Compose Multiplatform application for Android, iOS and Desktop.

# Customize after templating:

- Generate keystore to sign app [Android](https://developer.android.com/studio/publish/app-signing#generate-key)
- Splash screen for [Android](https://developer.android.com/develop/ui/views/launch/splash-screen) and [iOS](https://developer.apple.com/documentation/xcode/specifying-your-apps-launch-screen#Configure-a-launch-screen-in-an-information-property-list)
- Icons for [Android](https://developer.android.com/develop/ui/views/launch/icon_design_adaptive) and [iOS](https://makeappicon.com/)
- Homescreen shortcuts for [Android](https://developer.android.com/develop/ui/views/launch/shortcuts/creating-shortcuts) and [iOS](https://developer.apple.com/documentation/uikit/add-home-screen-quick-actions)
- Bundle identifier and package name
- Link Firebase project for [Android](https://firebase.google.com/docs/android/setup) and [iOS](https://firebase.google.com/docs/ios/setup)
- Associate web page for deep links with app on [Android](https://developer.android.com/studio/write/app-link-indexing) and [iOS](https://docs.flutter.dev/cookbook/navigation/set-up-universal-links)
- If you use Gitlab, configure Access Token in CI/CD variables to update screenshots via pipeline

# Run

Run configurations for Jetbrains IDEs are available in the [.run](./.run) directory.

| Platform         | Gradle task               | Notes                                                         |
|------------------|---------------------------|---------------------------------------------------------------|
| Android          | :composeApp:assembleDebug | This only assembles apk. Use IDE to also automatically launch |
| Android          |                           | Use run configuration to generate Baseline Profile separately |
| JVM              | :composeApp:run           |                                                               |
| JVM (hot reload) |                           | Run from IDE                                                  |
| iOS              |                           | Run from IDE                                                  |
| iOS - RELEASE    |                           | Run from IDE                                                  |
| iOS (XCode)      |                           | https://stackoverflow.com/a/78334953/12021861                 |

# Gradle tasks

| Task                          | Platform | Description                                                                   |
|-------------------------------|----------|-------------------------------------------------------------------------------|
| :composeApp:assembleDebug     | Android  | Assembles debug apk                                                           |
| :composeApp:bundleRelease     | Android  | Create release bundle for store distribution. Also generates baseline profile |
| :composeApp:jvmTest           | JVM      | Run common (Unit and UI) and architecture tests                               |
| :composeApp:testDebugUnitTest | Android  | Run common (Unit and UI) and screenshot tests                                 |
| :components:jvmTest           | JVM      | Run common (Unit and UI) for components module                                |

---

# Tags used in project

| Type    | Definition                              |
|---------|-----------------------------------------|
| TODO    | Thing to do                             |
| WAIT    | This change is waiting for some PR      |
| RELEASE | Things to do before releasing in stores |

# Useful links

- Libraries
    - [kmp-awesome](https://github.com/terrakok/kmp-awesome)
    - [Kotlin-Multiplatform-Libraries](https://github.com/AAkira/Kotlin-Multiplatform-Libraries)
    - [KmpEssentials](https://thearchitect123.github.io/ArtifactsDocProduction/develop/kotlin/multiplatform/kmpessentials/)
- Unstyled components
    - [lumo-ui](https://www.lumoui.com/)
    - [Compose unstyled](https://composables.com/docs/compose-unstyled/overview)
- [Compose SwiftUI bridge](https://touchlab.co/composeswiftbridge)

---

# Features

- Clean Architecture
- Navigation
    - [Primary Scaffold](./components/src/commonMain/kotlin/pl/inno4med/components/PrimaryScaffold.kt)
    - Homescreen shortcuts
    - Deeplinks
    - Platform Transitions
- [DI](https://insert-koin.io/docs/reference/koin-annotations/start)
- Storage
    - [Room](https://developer.android.com/kotlin/multiplatform/room)
    - [DataStore](https://developer.android.com/kotlin/multiplatform/datastore)
- [Network - Ktorfit](https://foso.github.io/Ktorfit/quick-start/)
- [RemoteMediator](./composeApp/src/commonMain/kotlin/pl/inno4med/asystent/core/RemoteMediator.kt)
- [Logger](https://kermit.touchlab.co/)
- [Result](./composeApp/src/commonMain/kotlin/pl/inno4med/asystent/utils/Result.kt)
- [Firebase Messaging for iOS and Android](https://firebase.google.com/docs/cloud-messaging)
- [In-App-Updates on Android](https://developer.android.com/guide/playcore/in-app-updates/kotlin-java)
- Splash screen
- [KmpEssentials](https://thearchitect123.github.io/ArtifactsDocProduction/develop/kotlin/multiplatform/kmpessentials/)
    - Toast
    - In App Review
- [Components module](./components)
    - [CustomError](./components/src/commonMain/kotlin/pl/inno4med/components/CustomError.kt)
    - [RetrySnackbar](./components/src/commonMain/kotlin/pl/inno4med/components/RetrySnackbar.kt)
    - [rememberNetwork](./components/src/commonMain/kotlin/pl/inno4med/components/HasNetwork.kt)
    - [AppBar](./components/src/commonMain/kotlin/pl/inno4med/components/AppBars.kt)
    - [Scroll indicator](./components/src/commonMain/kotlin/pl/inno4med/components/ScrollIndicator.kt)
- Utils
    - [DateTimeFormatter](./composeApp/src/commonMain/kotlin/pl/inno4med/asystent/utils/Formatters.kt)
    - [Mask for TextField](./composeApp/src/commonMain/kotlin/pl/inno4med/asystent/utils/MaskVisualTransformation.kt)
    - [Sort with Polish letters](./composeApp/src/commonMain/kotlin/pl/inno4med/asystent/utils/PolishCompare.kt)
- [Crashlytics](https://firebase.google.com/docs/crashlytics)
- [Baseline Profile](https://developer.android.com/topic/performance/baselineprofiles/overview)
- Tests
    - [Screenshot Tests in androidUnitTest](https://takahirom.github.io/roborazzi/top.html)
    - [Architecture tests](https://docs.konsist.lemonappdev.com/)
    - UI tests
- CI pipelines to run tests and update screenshots

# Things to improve

- Write tests for [ScrollIndicator](./components/src/commonMain/kotlin/pl/inno4med/components/ScrollIndicator.kt), WAIT https://github.com/takahirom/roborazzi/issues/757 https://github.com/takahirom/roborazzi/issues/754 https://github.com/Kotlin/kotlinx-kover/issues/772
- Maybe use [Preview](https://github.com/sergio-sastre/Android-screenshot-testing-playground) annotation for screenshot tests in future
- Can't run commonTests on iOS https://github.com/RevenueCat/purchases-kmp/issues/420 https://github.com/getsentry/sentry-kotlin-multiplatform/issues/176
- [AboutLibraries](https://github.com/mikepenz/AboutLibraries#setup)
- [Detekt](https://github.com/detekt/detekt)
- [BlockHound](https://nitin-karthy.medium.com/using-blockhound-with-kotlin-7aa0a3f28364)

# Inspiration

[nowinandroid](https://github.com/android/nowinandroid/tree/main), [CMPMemeCreator](https://github.com/philipplackner/CMPMemeCreator), [Open-API-Android-App](https://github.com/mitchtabian/Open-API-Android-App), [kmp-movie](https://github.com/piashcse/kmp-movie), [CryptoTracker](https://github.com/philipplackner/CryptoTracker), [architecture-samples](https://github.com/android/architecture-samples), [Android - Clean Architecture - Kotlin](https://github.com/android10/Android-CleanArchitecture-Kotlin), [MVVMTodo](https://github.com/codinginflow/MVVMTodo), [Clean-Notes](https://github.com/mitchtabian/Clean-Notes), [AnimeListApp](https://github.com/daniatitienei/AnimeListApp), [NoteAppKMM](https://github.com/philipplackner/NoteAppKMM), [CleanArchitectureNoteApp](https://github.com/philipplackner/CleanArchitectureNoteApp/tree/app) 
