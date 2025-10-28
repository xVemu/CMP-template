package pl.inno4med.asystent

import android.content.ContentProvider
import android.os.Build
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.util.Logger

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(
    sdk = [Build.VERSION_CODES.BAKLAVA],
    qualifiers = "w411dp-h914dp-normal-long-notround-any-420dpi-keyshidden-nonav"
)
abstract class ScreenshotTest() {
    @Before
    fun setup() {
        setupAndroidContextProvider()
    }

    // Configures Compose's AndroidContextProvider to access resources in tests.
    // WAIT https://youtrack.jetbrains.com/issue/CMP-6612 https://github.com/JetBrains/compose-multiplatform/pull/5433
    private fun setupAndroidContextProvider() {
        val type = findAndroidContextProvider() ?: return
        Robolectric.setupContentProvider(type)
    }

    private fun findAndroidContextProvider(): Class<ContentProvider>? {
        val providerClassName = "org.jetbrains.compose.resources.AndroidContextProvider"
        return try {
            @Suppress("UNCHECKED_CAST")
            Class.forName(providerClassName) as Class<ContentProvider>
        } catch (_: ClassNotFoundException) {
            Logger.debug("Class not found: $providerClassName")
            // Tests that don't depend on Compose will not have the provider class in classpath and will get
            // ClassNotFoundException. Skip configuring the provider for them.
            null
        }
    }
}
