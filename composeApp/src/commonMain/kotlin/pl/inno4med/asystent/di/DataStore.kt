package pl.inno4med.asystent.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.core.annotation.Single

@Single
expect fun provideDataStore(contextW: ContextWrapper): DataStore<Preferences>

fun createDataStore(producePath: () -> String) =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

const val dataStoreFileName = "main.preferences_pb"

