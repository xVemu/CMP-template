package pl.inno4med.asystent.utils

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import pl.inno4med.components.CustomError
import pl.inno4med.components.Loading

@Suppress("ComposableNaming")
sealed class Result<out T> {
    data class Success<out T>(
        val body: T,
        val error: Exception? = null,
        val refreshing: Boolean = false,
    ) : Result<T>()

    data class Failure(val error: Exception) : Result<Nothing>()

    data object Loading : Result<Nothing>()

    val isSuccess = this is Success
    val asSuccess = this as? Success
    val value = asSuccess?.body

    val isFailure = this is Failure
    val asFailure = this as? Failure

    val isLoading = this is Loading
    val asLoading = this as? Loading

    @Composable
    fun switch(
        retry: UnitCallback?,
        loading: UnitComposable? = null,
        failure: (@Composable (error: Exception) -> Unit)? = null,
        success: @Composable (body: T) -> Unit,
    ) {
        Crossfade(this) { finalResult ->
            when (finalResult) {
                is Loading -> loading?.invoke() ?: Loading()
                is Success -> success(finalResult.body)
                is Failure -> failure?.invoke(finalResult.error) ?: CustomError(retry = retry)
            }
        }
    }

    @Composable
    fun switchRefresh(
        retry: UnitCallback?,
        loading: UnitComposable? = null,
        failure: (@Composable (error: Exception) -> Unit)? = null,
        success: @Composable (body: T, refreshing: Boolean, error: Exception?) -> Unit,
    ) {
        when (this) {
            is Loading -> loading?.invoke() ?: Loading()
            is Success -> success(body, refreshing, error)
            is Failure -> failure?.invoke(error) ?: CustomError(retry = retry)
        }
    }

    @Composable
    fun switchPlaceholder(
        retry: UnitCallback?,
        emptyItem: @UnsafeVariance T,
        loading: UnitComposable? = null,
        failure: (@Composable (error: Exception) -> Unit)? = null,
        success: @Composable (body: T) -> Unit,
    ) = switch(
        retry,
        {
            Box(
                Modifier.clearAndSetSemantics { }) {
                success(emptyItem)
            }
        },
        failure, success,
    )

    @Composable
    fun switchPlaceholderRefresh(
        retry: UnitCallback?,
        emptyItem: @UnsafeVariance T,
        loading: UnitComposable? = null,
        failure: (@Composable (error: Exception) -> Unit)? = null,
        success: @Composable (body: T, refreshing: Boolean, error: Exception?) -> Unit,
    ) = switchRefresh(
        retry,
        {
            Box(Modifier.clearAndSetSemantics { }) {
                success(emptyItem, true, null)
            }
        },
        failure, success,
    )
}

typealias ResultList<T> = Result<List<T>>
typealias ResultMap<K, V> = Result<Map<K, V>>
typealias ResultMapList<K, V> = Result<Map<K, List<V>>>
