package pl.inno4med.asystent

import com.lemonappdev.konsist.api.KoModifier
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.isSortedByName
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withAllModifiers
import com.lemonappdev.konsist.api.ext.list.properties
import com.lemonappdev.konsist.api.ext.list.returnTypes
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class GeneralTest {
    @Test
    fun `enums has alphabetically ordered consts`() {
        Konsist
            .scopeFromProduction()
            .classes()
            .withAllModifiers(KoModifier.ENUM)
            .assertTrue { it.enumConstants.isSortedByName() }
    }

    @Test
    fun `no field should have 'm' prefix`() {
        Konsist
            .scopeFromProject()
            .classes()
            .properties()
            .assertFalse {
                val secondCharacterIsUppercase = it.name.getOrNull(1)?.isUpperCase() ?: false
                it.name.startsWith('m') && secondCharacterIsUppercase
            }
    }

    @Test
    fun `package name must match file path`() {
        Konsist
            .scopeFromProject()
            .packages
            .assertTrue { it.hasMatchingPath }
    }

    @Test
    fun `return type of all functions are immutable`() {
        Konsist
            .scopeFromProject()
            .functions()
            .returnTypes
            .assertFalse { it.isMutableType }
    }

    @Test
    fun `no empty files allowed`() {
        Konsist
            .scopeFromProject()
            .files
            .assertFalse { it.text.isEmpty() }
    }
}
