package pl.inno4med.asystent.utils

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class FormattersTest {
    @Test
    fun testAsPhoneNumber() {
        "511111111".asPhoneNumber() shouldBe "511 111 111"
    }
}
