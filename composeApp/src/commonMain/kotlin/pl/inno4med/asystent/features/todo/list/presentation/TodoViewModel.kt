package pl.inno4med.asystent.features.todo.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import pl.inno4med.asystent.features.todo.list.domain.Todo
import pl.inno4med.asystent.features.todo.list.domain.usecases.GetTodosUseCase
import pl.inno4med.asystent.utils.Result
import pl.inno4med.asystent.utils.ResultList

@KoinViewModel
class TodoViewModel(
    private val getTodosUseCase: GetTodosUseCase,
) : ViewModel() {
    private val _todos = MutableStateFlow<ResultList<Todo>>(Result.Loading)
    val todos = _todos.asStateFlow()

    init {
        refreshTodos()
    }

    private var job: Job? = null

    fun refreshTodos() {
        job?.cancel()
        job = viewModelScope.launch {
            _todos.emitAll(getTodosUseCase())
        }
    }
}
