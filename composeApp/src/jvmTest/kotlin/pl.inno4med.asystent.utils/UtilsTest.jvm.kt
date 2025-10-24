package pl.inno4med.asystent.utils

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class UtilsTestJvm {
    @Test
    fun getMapUrl() {
        val latitude = 52.2297
        val longitude = 21.0122
        val label = "Warsaw"
        val uri = getMapUrl(latitude, longitude, label)

        uri.toString() shouldBe "https://maps.google.com/maps?q=52.2297%2C21.0122(Warsaw)"
    }
}
