package pl.inno4med.asystent.features.todo.list.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TodoDao {
    @Query("SELECT * FROM TodoEntity")
    suspend fun getAllTodos(): List<TodoEntity>

    @Upsert
    suspend fun insertAll(todos: List<TodoEntity>)

    @Query("DELETE FROM TodoEntity")
    suspend fun deleteAllTodos()

    suspend fun deleteAndInsertAll(todos: List<TodoEntity>) {
        deleteAllTodos()
        insertAll(todos)
    }
}
