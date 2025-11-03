package pl.inno4med.asystent.features.todo.list.domain.usecases

import pl.inno4med.asystent.features.todo.list.domain.TodoRepo

class GetTodosUseCase(private val todoRepo: TodoRepo) {
    operator fun invoke() = todoRepo.getTodos()
}
