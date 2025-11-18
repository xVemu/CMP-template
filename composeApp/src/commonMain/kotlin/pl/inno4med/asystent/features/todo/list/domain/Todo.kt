package pl.inno4med.asystent.features.todo.list.domain

data class Todo(
    val id: Long,
    val name: String,
    val image: String,
) {
    companion object {
        fun empty() = Todo(0, "rsaahsahneirssr", "rsaahsarhrsahneirsanheihasrnhashnasr")
    }
}
