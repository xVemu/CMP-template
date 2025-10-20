package pl.inno4med.components

internal fun String.capitalize() = lowercase().replaceFirstChar { it.uppercase() }
