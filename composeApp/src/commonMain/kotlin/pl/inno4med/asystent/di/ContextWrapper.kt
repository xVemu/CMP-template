package pl.inno4med.asystent.di

import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

expect class ContextWrapper

@Single
expect fun provideContextWrapper(scope: Scope): ContextWrapper
