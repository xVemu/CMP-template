package pl.inno4med.asystent.features.todo.list.pretentation

import com.github.takahirom.roborazzi.captureRoboImage
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.robolectric.annotation.Config
import pl.inno4med.asystent.ScreenshotTest
import pl.inno4med.asystent.features.todo.list.domain.Todo
import pl.inno4med.asystent.features.todo.list.presentation.TodoList
import pl.inno4med.asystent.features.todo.list.presentation.TodoViewModel
import pl.inno4med.asystent.theme.AppTheme
import pl.inno4med.asystent.utils.Result
import kotlin.test.Test

class TodoListTest : ScreenshotTest() {

    private val vm: TodoViewModel = mockk {
        every { todos } returns MutableStateFlow(
            Result.Success(
                listOf(
                    Todo(1, "Task 1", "https://example.com/image1.png"),
                    Todo(2, "Task 2", "https://example.com/image2.png"),
                    Todo(3, "Task 3", "https://example.com/image3.png"),
                )
            )
        )
    }

    @Test
    fun todoListTest() {
        captureRoboImage {
            TodoList("Test", vm)
        }
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightMode() {
        captureRoboImage {
            AppTheme {
                TodoList("Test", vm)
            }
        }
    }
}
