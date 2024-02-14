package com.me.data.utils

import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

const val STRING_LENGTH = 140
private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') +
        ' ' + ' ' + ' ' + ' ' + ' ' + ' ' + ' ' + ' ' + ' ' + ' ' + ' '

fun randomString(): String = ThreadLocalRandom.current()
    .ints(STRING_LENGTH.toLong(), 0, charPool.size)
    .asSequence()
    .map(charPool::get)
    .joinToString("")
