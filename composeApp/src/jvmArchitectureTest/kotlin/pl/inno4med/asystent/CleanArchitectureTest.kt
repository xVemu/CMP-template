package pl.inno4med.asystent

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.verify.assertTrue
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinApplication
import kotlin.test.Test

class CleanArchitectureTest {

    private val presentationLayer =
        Layer("Presentation", "pl.inno4med.asystent.features..presentation..")
    private val domainLayer = Layer("Domain", "pl.inno4med.asystent.features..domain..")
    private val dataLayer = Layer("Data", "pl.inno4med.asystent.features..data..")

    @Test
    fun `correct packages dependencies`() {
        Konsist
            .scopeFromProject()
            .assertArchitecture {
                presentationLayer.dependsOn(domainLayer)
                presentationLayer.doesNotDependOn(dataLayer)

                domainLayer.dependsOnNothing()

                dataLayer.dependsOn(domainLayer)
                dataLayer.doesNotDependOn(presentationLayer)
            }
    }

    @Test
    fun `Koin Configuration should be only in DefaultConfiguration`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withAnnotationOf(Configuration::class, KoinApplication::class)
            .assertTrue {
                it.resideInPackage("pl.inno4med.asystent.di..")
            }
    }
}
