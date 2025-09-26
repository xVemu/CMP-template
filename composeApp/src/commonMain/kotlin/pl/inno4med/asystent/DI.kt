package pl.inno4med.asystent

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.koinConfiguration
import org.koin.ksp.generated.module

@Module
@ComponentScan
class DefaultModule

val DefaultKoinConfiguration = koinConfiguration {
    DefaultModule().module
    DatabaseModule().module
}
