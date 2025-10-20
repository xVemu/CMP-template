This is a Kotlin Multiplatform project targeting Android, iOS, Desktop (JVM).

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform
  applications.
  It contains several subfolders:
    - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
    - Other folders are for Kotlin code that will be compiled for only the platform indicated in the
      folder name.
      For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
      Similarly, if you want to edit the Desktop (JVM) specific part,
      the [jvmMain](./composeApp/src/jvmMain/kotlin)
      folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose
  Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for
  your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run
widget
in your IDE’s toolbar or build it directly from the terminal:

- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run
widget
in your IDE’s toolbar or run it directly from the terminal:

- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run
widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more
about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

Libraries:

- https://github.com/terrakok/kmp-awesome
- https://github.com/AAkira/Kotlin-Multiplatform-Libraries

DONE:

- Clean Architecture
- Tests
    - [MockK](https://mockk.io/)
    - Kotlin test
    - [Kotest assertions](https://kotest.io/docs/assertions/assertions.html)
- [DI](https://insert-koin.io/docs/reference/koin-annotations/start)
- [Room](https://developer.android.com/kotlin/multiplatform/room)
- [DataStore](https://developer.android.com/kotlin/multiplatform/datastore)
- [Ktorfit](https://foso.github.io/Ktorfit/quick-start/)
- [DateTimeFormatter](./composeApp/src/commonMain/kotlin/pl/inno4med/asystent/utils/Formatters.kt)
- [Components module](./components)
    - [CustomError](./components/src/commonMain/kotlin/pl/inno4med/components/CustomError.kt)
    - [RetrySnackbar](./components/src/commonMain/kotlin/pl/inno4med/components/RetrySnackbar.kt)
  - [rememberNetwork](./components/src/commonMain/kotlin/pl/inno4med/components/HasNetwork.kt)
  - [AppBar](./components/src/commonMain/kotlin/pl/inno4med/components/AppBars.kt)
- [Logger](https://kermit.touchlab.co/)
- [Result](./composeApp/src/commonMain/kotlin/pl/inno4med/asystent/utils/Result.kt)
- [KmpEssentials](https://thearchitect123.github.io/ArtifactsDocProduction/develop/kotlin/multiplatform/kmpessentials/)
    - Toast
    - In App Review
- [Firebase Messaging for iOS and Android](https://firebase.google.com/docs/cloud-messaging)
- [Sort with Polish letters](./composeApp/src/commonMain/kotlin/pl/inno4med/asystent/utils/PolishCompare.kt)
- [Mask for TextField](./composeApp/src/commonMain/kotlin/pl/inno4med/asystent/utils/MaskVisualTransformation.kt)
- [In-App-Updates on Android](https://developer.android.com/guide/playcore/in-app-updates/kotlin-java)
- Navigation
  - [Primary Scaffold](./components/src/commonMain/kotlin/pl/inno4med/components/PrimaryScaffold.kt)
  - Homescreen shortcuts
  - Deeplinks
  - Platform Transitions
- Splash screen
- [Crashlytics](https://firebase.google.com/docs/crashlytics)
- [Baseline Profile](https://developer.android.com/topic/performance/baselineprofiles/overview)

TODO:

- tests
- unstyled
- przepisać https://github.com/Milad-Akarie/skeletonizer/tree/main/lib/src
- scroll indicator

Customize after templating:

- keystore to sign app https://developer.android.com/studio/publish/app-signing#generate-key
- splash screen https://developer.android.com/develop/ui/views/launch/splash-screen https://developer.apple.com/documentation/xcode/specifying-your-apps-launch-screen#Configure-a-launch-screen-in-an-information-property-list
- icons https://developer.android.com/develop/ui/views/launch/icon_design_adaptive https://makeappicon.com/
- shortcuts https://developer.android.com/develop/ui/views/launch/shortcuts/creating-shortcuts https://developer.apple.com/documentation/uikit/add-home-screen-quick-actions
- bundle id
- Link Firebase project https://firebase.google.com/docs/android/setup https://firebase.google.com/docs/ios/setup
- Associate deep link with app https://developer.android.com/studio/write/app-link-indexing https://docs.flutter.dev/cookbook/navigation/set-up-universal-links
- [To run project from XCode](https://stackoverflow.com/a/78334953/12021861)
- https://github.com/mikepenz/AboutLibraries#setup
