package pl.inno4med.asystent.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.koinConfiguration
import org.koin.ksp.generated.module
import pl.inno4med.asystent.DatabaseModule

@Module
@ComponentScan
class DefaultModule

val DefaultKoinConfiguration = koinConfiguration {
    DefaultModule().module
    DatabaseModule().module
    NetworkModule().module
}
