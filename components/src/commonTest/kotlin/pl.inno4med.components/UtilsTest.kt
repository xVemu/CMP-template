package pl.inno4med.components

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class UtilsTest {
    @Test
    fun capitalize() {
        "hELLO wORLD".capitalize() shouldBe "Hello world"

        "HELLO".capitalize() shouldBe "Hello"

        "kOtLiN".capitalize() shouldBe "Kotlin"

        "TesTinG".capitalize() shouldBe "Testing"
    }
}
