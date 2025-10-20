package pl.inno4med.asystent.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.KoinApplication
import org.koin.core.annotation.Module
import pl.inno4med.asystent.features.todo.list.TodoListModule

@Module
@Configuration
@ComponentScan
class DefaultModule

@KoinApplication
object MainKoinApplication

// Sometimes explicit return type in providers is needed, otherwise koin-annotations generator behaves unexpectedly
@Module(includes = [TodoListModule::class])
@Configuration
class FeatureModule
