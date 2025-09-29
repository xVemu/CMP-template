package pl.inno4med.asystent.di

import org.koin.core.annotation.Single
import java.io.File

@Single
actual fun provideDataStore(contextW: ContextWrapper) = createDataStore {
    val file = File(System.getProperty("java.io.tmpdir"), dataStoreFileName)
    file.absolutePath
}
