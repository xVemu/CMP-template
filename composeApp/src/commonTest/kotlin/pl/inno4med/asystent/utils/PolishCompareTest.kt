package pl.inno4med.asystent.utils

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class PolishCompareTest {
    @Test
    fun polishCompareTest() {
        val list = listOf("łabędź", "lol", "mont", "zaraz", "alfabet")
        list.sortedPolish() shouldBe listOf("alfabet", "lol", "łabędź", "mont", "zaraz")
    }
}
