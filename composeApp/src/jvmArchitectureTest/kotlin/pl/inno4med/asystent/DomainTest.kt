package pl.inno4med.asystent

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class DomainTest {
    @Test
    fun `domain should not contain libraries import`() {
        val allowedImports = listOf(
            "kotlinx..",
            "pl.inno4med.asystent.features..domain..",
            "pl.inno4med.asystent.utils..",
        ).map { it.replace("..", ".*").toRegex() }

        Konsist
            .scopeFromPackage("pl.inno4med.asystent.features..domain..")
            .imports
            .assertTrue { import ->
                allowedImports.any(import::hasNameMatching)
            }
    }


    @Test
    fun `classes with 'UseCase' suffix should reside in 'domain'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..domain..") }
    }

    @Test
    fun `classes with 'UseCase' suffix should have single 'public operator' method named 'invoke'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue {
                val hasSingleInvokeOperatorMethod = it.hasFunction { function ->
                    function.name == "invoke" && function.hasPublicOrDefaultModifier && function.hasOperatorModifier
                }

                hasSingleInvokeOperatorMethod && it.countFunctions { item -> item.hasPublicOrDefaultModifier } == 1
            }
    }
}
