package pl.inno4med.asystent.features.todo.list.domain.usecases

import org.koin.core.annotation.Factory
import pl.inno4med.asystent.features.todo.list.domain.TodoRepo

@Factory
class GetTodosUseCase(private val todoRepo: TodoRepo) {
    suspend operator fun invoke() = todoRepo.getTodos()
}
