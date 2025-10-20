package pl.inno4med.asystent.features.todo.list.data

import de.jensklingenberg.ktorfit.http.GET

interface TodoApi {
    @GET("beers/ale")
    suspend fun getTodos(): List<TodoDto>
}
