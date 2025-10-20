package pl.inno4med.asystent.features.todo.list.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.inno4med.asystent.features.todo.list.domain.Todo

@Entity
data class TodoEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val image: String,
) {
    fun toDomain() = Todo(
        id = id,
        name = name,
        image = image,
    )
}
