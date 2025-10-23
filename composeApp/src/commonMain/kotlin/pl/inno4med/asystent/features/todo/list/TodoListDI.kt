package pl.inno4med.asystent.features.todo.list

import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import pl.inno4med.asystent.di.MainDatabase
import pl.inno4med.asystent.features.todo.list.data.TodoApi
import pl.inno4med.asystent.features.todo.list.data.TodoDao
import pl.inno4med.asystent.features.todo.list.data.createTodoApi

@Module
@ComponentScan
class TodoListModule {
    @Single
    fun provideTodoApi(ktorfit: Ktorfit): TodoApi = ktorfit.createTodoApi()

    @Single
    fun provideTodoDao(database: MainDatabase): TodoDao = database.todoDao()
}
