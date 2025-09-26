package pl.inno4med.asystent

import androidx.room.ConstructedBy
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
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
class DatabaseModule {

    @Single
    fun provideDatabase() =
        getDatabasePlatformBuilder()
            .fallbackToDestructiveMigration(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()

    @Single
    fun provideTodoDao(db: MainDatabase) = db.todoDao()
}

expect fun getDatabasePlatformBuilder(): RoomDatabase.Builder<MainDatabase>

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
) : KoinComponent

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(item: TodoEntity)

    @Query("SELECT count(*) FROM TodoEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM TodoEntity")
    fun getAllAsFlow(): Flow<List<TodoEntity>>
}
