package pl.inno4med.asystent.di

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

fun getLocalAppDataDir(appName: String = "Asystent"): File {
    val env = System.getenv()
    val os = System.getProperty("os.name")?.lowercase() ?: ""
    val basePath = when {
        os.contains("win") -> env["LOCALAPPDATA"] ?: File(
            System.getProperty("user.home"),
            "AppData\\Local"
        ).absolutePath

        os.contains("mac") || os.contains("darwin") -> File(
            System.getProperty("user.home"),
            "Library/Application Support"
        ).absolutePath

        else -> env["XDG_DATA_HOME"] ?: File(
            System.getProperty("user.home"),
            ".local/share"
        ).absolutePath
    }
    val dir = File(basePath, appName)
    if (!dir.exists()) dir.mkdirs()
    return dir
}

actual fun getDatabasePlatformBuilder(contextW: ContextWrapper): RoomDatabase.Builder<MainDatabase> {
    val dbFile = File(getLocalAppDataDir(), "main.db")
    return Room.databaseBuilder<MainDatabase>(
        name = dbFile.absolutePath,
    )
}
