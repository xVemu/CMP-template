package pl.inno4med.asystent.features.todo.details.domain

import pl.inno4med.asystent.features.todo.list.domain.TodoRepo

class GetTodoDetailsUseCase(private val todoRepo: TodoRepo) {
    suspend operator fun invoke(id: Long) = todoRepo.getTodoById(id)
}
