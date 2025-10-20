package pl.inno4med.asystent.features.todo.list.domain.usecases

import pl.inno4med.asystent.features.todo.list.domain.TodoRepo

class AddTodoUseCase(private val todoRepo: TodoRepo) {
    suspend operator fun invoke() = todoRepo.getTodos()
}
