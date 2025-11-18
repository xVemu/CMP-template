package pl.inno4med.asystent.features.todo.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import pl.inno4med.asystent.features.todo.details.domain.GetTodoDetailsUseCase
import pl.inno4med.asystent.features.todo.list.domain.Todo
import pl.inno4med.asystent.utils.Result

@KoinViewModel
class TodoDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTodoDetailsUseCase: GetTodoDetailsUseCase,
) : ViewModel() {
    private val _todo = MutableStateFlow<Result<Todo>>(Result.Loading)
    val todo = _todo.asStateFlow()

    private val todoId = savedStateHandle.get<Long>("todoId")!!

    init {
        loadTodo()
    }

    private var job: Job? = null

    fun loadTodo() {
        job?.cancel()
        job = viewModelScope.launch {
            _todo.value = Result.Loading
            _todo.value = try {
                Result.Success(getTodoDetailsUseCase(todoId))
            } catch (e: Exception) {
                ensureActive()

                Result.Failure(e)
            }
        }
    }
}
