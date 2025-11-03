package pl.inno4med.asystent

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class DataTest {
    @Test
    fun `every interface in domain should have implementation in data`() {
        Konsist
            .scopeFromPackage("pl.inno4med.asystent.features..domain..")
            .interfaces()
            .assertTrue { domainInterface ->
                domainInterface.children().all {
                    it.resideInPackage("pl.inno4med.asystent.features..data..")
                }
            }
    }
}
