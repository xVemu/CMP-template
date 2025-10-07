package pl.inno4med.asystent.utils

import kotlin.math.sign

private val letters = linkedSetOf(
    'a', 'ą', 'b', 'c', 'ć', 'd', 'e', 'ę', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'ł',
    'm', 'n', 'ń', 'o', 'ó', 'p', 'q', 'r', 's', 'ś', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ż', 'ź'
)

fun polishCompare(a: String, b: String): Int {
    val lowerA = a.lowercase()
    val lowerB = b.lowercase()
    val minLength = minOf(lowerA.length, lowerB.length)
    for (i in 0 until minLength) {
        val aValue = letters.indexOf(lowerA[i])
        val bValue = letters.indexOf(lowerB[i])
        val result = (aValue - bValue).sign
        if (result != 0) return result
    }
    return (lowerA.length - lowerB.length).sign
}

fun List<String>.sortedPolish(): List<String> = sortedWith(::polishCompare)
