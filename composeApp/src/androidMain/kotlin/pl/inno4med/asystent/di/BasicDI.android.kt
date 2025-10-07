package pl.inno4med.asystent.di

import android.content.Context
import org.koin.core.annotation.Single
import org.koin.core.scope.Scope

actual class ContextWrapper(val context: Context)

@Single
actual fun provideContextWrapper(scope: Scope) = ContextWrapper(scope.get())
