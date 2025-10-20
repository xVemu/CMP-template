package pl.inno4med.asystent.features.todo.list.domain

import kotlinx.coroutines.flow.Flow
import pl.inno4med.asystent.utils.ResultList

interface TodoRepo {
    suspend fun getTodos(): Flow<ResultList<Todo>>

    suspend fun addTodo(todo: Todo)
}
