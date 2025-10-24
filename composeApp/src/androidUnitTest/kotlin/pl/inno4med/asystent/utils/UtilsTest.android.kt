package pl.inno4med.asystent.utils

import io.kotest.matchers.shouldBe
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
class UtilsTestAndroid {
    @Test
    fun getMapUrl() {
        val latitude = 37.7749
        val longitude = -122.4194
        val label = "San Francisco"
        val uri = getMapUrl(latitude, longitude, label)

        uri.toString() shouldBe "https://maps.google.com/maps?q=37.7749%2C-122.4194(San%20Francisco)"
    }
}
