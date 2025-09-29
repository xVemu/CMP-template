package pl.inno4med.asystent.di

import org.koin.core.annotation.Single

@Single
actual fun provideDataStore(contextW: ContextWrapper) =
    createDataStore { contextW.context.filesDir.resolve(dataStoreFileName).absolutePath }
