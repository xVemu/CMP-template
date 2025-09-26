package pl.inno4med.asystent

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.java.KoinJavaComponent

actual fun getDatabasePlatformBuilder(): RoomDatabase.Builder<MainDatabase> {
    val context: Context = KoinJavaComponent.get(Context::class.java)
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("main.db")
    return Room.databaseBuilder<MainDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
