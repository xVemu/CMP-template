package pl.inno4med.asystent

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import pl.inno4med.asystent.di.ContextWrapper
import pl.inno4med.asystent.features.todo.list.data.TodoDao
import pl.inno4med.asystent.features.todo.list.data.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
@ConstructedBy(MainDatabaseConstructor::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object MainDatabaseConstructor : RoomDatabaseConstructor<MainDatabase> {
    override fun initialize(): MainDatabase
}

@Module
@Configuration
class DatabaseModule {
    @Single
    fun provideDatabase(contextW: ContextWrapper) =
        getDatabasePlatformBuilder(contextW)
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
}

expect fun getDatabasePlatformBuilder(contextW: ContextWrapper): RoomDatabase.Builder<MainDatabase>
