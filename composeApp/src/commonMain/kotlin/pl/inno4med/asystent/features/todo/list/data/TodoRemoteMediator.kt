package pl.inno4med.asystent.features.todo.list.data

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import pl.inno4med.asystent.features.todo.list.domain.Todo
import pl.inno4med.asystent.features.todo.list.domain.TodoRepo
import pl.inno4med.asystent.utils.Result
import pl.inno4med.asystent.utils.ResultList

@Single
class TodoRemoteMediator(private val todoDao: TodoDao, private val todoApi: TodoApi) : TodoRepo {
    override fun getTodos(): Flow<ResultList<Todo>> =
        flow {
            coroutineScope {
                try {
                    val networkDefer = async { todoApi.getTodos() }

                    val local = todoDao.getAllTodos().map { it.toDomain() }

                    if (local.isNotEmpty())
                        emit(Result.Success(local, refreshing = true))
                    else emit(Result.Loading)

                    val network = try {
                        networkDefer.await()
                    } catch (e: Exception) {
                        ensureActive()
                        if (local.isNotEmpty())
                            return@coroutineScope emit(Result.Success(local, error = e))

                        throw e
                    }

                    emit(Result.Success(network.map { it.toDomain() }))

                    todoDao.deleteAndInsertAll(network.map { it.toEntity() })
                } catch (e: Exception) {
                    ensureActive()
                    emit(Result.Failure(e))
                }
            }
        }

    override suspend fun addTodo(todo: Todo) {
        TODO("Not yet implemented")
    }
}
