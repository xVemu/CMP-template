package pl.inno4med.asystent.features.todo.details

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import pl.inno4med.asystent.features.todo.details.domain.GetTodoDetailsUseCase
import pl.inno4med.asystent.features.todo.list.domain.TodoRepo

@Module
@ComponentScan
class TodoDetailsModule {
    @Factory
    fun provideGetTodoDetailsUseCase(todoRepo: TodoRepo) = GetTodoDetailsUseCase(todoRepo)
}
