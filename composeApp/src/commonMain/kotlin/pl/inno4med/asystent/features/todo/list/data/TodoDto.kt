package pl.inno4med.asystent.features.todo.list.data

import kotlinx.serialization.Serializable
import pl.inno4med.asystent.features.todo.list.domain.Todo

@Serializable
data class TodoDto(
    val name: String,
    val image: String,
    val id: Long,
) {
    fun toDomain() = Todo(
        name = name,
        image = image,
        id = id,
    )

    fun toEntity() = TodoEntity(
        id = id,
        name = name,
        image = image,
    )
}
