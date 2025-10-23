package pl.inno4med.asystent.di

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual fun getDatabasePlatformBuilder(contextW: ContextWrapper): RoomDatabase.Builder<MainDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "main.db")
    return Room.databaseBuilder<MainDatabase>(
        name = dbFile.absolutePath,
    )
}
