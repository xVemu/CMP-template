package pl.inno4med.asystent.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.dsl.koinConfiguration
import org.koin.ksp.generated.module
import pl.inno4med.asystent.DatabaseModule
import pl.inno4med.asystent.features.todo.list.TodoListModule

@Module
@ComponentScan
class DefaultModule

val DefaultKoinConfiguration = koinConfiguration {
    modules(
        DefaultModule().module,
        DatabaseModule().module,
        NetworkModule().module,

        TodoListModule().module,
    )
    // Sometimes explicit return type in providers is needed, otherwise koin-annotations generator behaves unexpectedly
}
