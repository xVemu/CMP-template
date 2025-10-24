package pl.inno4med.asystent.utils

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class UtilsTestIos {
    @Test
    fun getMapUrl() {
        val latitude = 37.7749
        val longitude = -122.4194
        val label = "San Francisco"
        val uri = getMapUrl(latitude, longitude, label)

        uri.toString() shouldBe "https://maps.apple.com/maps?ll=37.7749,-122.4194&q=San Francisco"
    }
}
