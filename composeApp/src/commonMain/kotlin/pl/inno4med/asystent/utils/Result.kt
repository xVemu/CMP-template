package pl.inno4med.asystent.utils

import kotlin.jvm.JvmInline

sealed interface Result<out T> {
    class Success<out T>(
        val value: T,
        val error: Exception? = null,
        val refreshing: Boolean = false,
    ) : Result<T> {
        operator fun component1() = value
        operator fun component2() = error
        operator fun component3() = refreshing
    }

    @JvmInline
    value class Failure(val error: Exception) : Result<Nothing> {
        operator fun component1() = error
    }

    data object Loading : Result<Nothing>
}

typealias ResultList<T> = Result<List<T>>
