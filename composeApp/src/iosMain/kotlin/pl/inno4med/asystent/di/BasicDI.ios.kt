package pl.inno4med.asystent.di

import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

actual class ContextWrapper

@Single
actual fun provideContextWrapper(scope: Scope) = ContextWrapper()
