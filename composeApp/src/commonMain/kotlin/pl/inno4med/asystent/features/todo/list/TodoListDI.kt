package pl.inno4med.asystent.features.todo.list

import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import pl.inno4med.asystent.di.MainDatabase
import pl.inno4med.asystent.features.todo.list.data.TodoApi
import pl.inno4med.asystent.features.todo.list.data.TodoDao
import pl.inno4med.asystent.features.todo.list.data.createTodoApi
import pl.inno4med.asystent.features.todo.list.domain.TodoRepo
import pl.inno4med.asystent.features.todo.list.domain.usecases.GetTodosUseCase

@Module
@ComponentScan
class TodoListModule {
    @Single
    fun provideTodoApi(ktorfit: Ktorfit): TodoApi = ktorfit.createTodoApi()

    @Single
    fun provideTodoDao(database: MainDatabase): TodoDao = database.todoDao()

    @Factory
    fun provideGetTodosUseCase(todoRepo: TodoRepo) = GetTodosUseCase(todoRepo)
}
