package data.utils

import kotlin.random.Random

const val STRING_LENGTH = 140
private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') +
        ' ' + ' ' + ' ' + ' ' + ' ' + ' ' + ' ' + ' ' + ' ' + ' ' + ' '

fun randomString(): String = generateSequence {
    Random.Default.nextInt(0, charPool.size)
}.take(STRING_LENGTH)
    .toList()
    .map(charPool::get)
    .joinToString("")
