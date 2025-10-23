package pl.inno4med.asystent.di

import androidx.room.Room
import androidx.room.RoomDatabase

actual fun getDatabasePlatformBuilder(contextW: ContextWrapper): RoomDatabase.Builder<MainDatabase> {
    val appContext = contextW.context.applicationContext
    val dbFile = appContext.getDatabasePath("main.db")
    return Room.databaseBuilder<MainDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
