package pl.inno4med.asystent.features.todo.list.data

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import pl.inno4med.asystent.core.RemoteMediator
import pl.inno4med.asystent.features.todo.list.domain.Todo
import pl.inno4med.asystent.features.todo.list.domain.TodoRepo
import pl.inno4med.asystent.utils.ResultList

@Single
class TodoRemoteMediator(private val todoDao: TodoDao, private val todoApi: TodoApi) : TodoRepo,
    RemoteMediator {
    override fun getTodos(): Flow<ResultList<Todo>> =
        sync(
            getRemote = todoApi::getTodos,
            getLocal = {
                todoDao.getAllTodos().takeIf { it.isNotEmpty() }?.map { it.toDomain() }
            },
            mapDtoToDomain = { dto -> dto.map { it.toDomain() } },
            deleteAndInsert = { network ->
                todoDao.deleteAndInsertAll(network.map { it.toEntity() })
            }
        )

    override suspend fun addTodo(todo: Todo) {
        TODO("Not yet implemented")
    }
}
