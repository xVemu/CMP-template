package pl.inno4med.components

fun String.capitalize() = lowercase().replaceFirstChar { it.uppercase() }
