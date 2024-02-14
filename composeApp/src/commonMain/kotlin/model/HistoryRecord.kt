package com.me.model

import androidx.compose.ui.graphics.Color
import com.me.data.utils.randomString

interface HistoryRecord {
    val text: String
    val feelings: List<Feeling>
    val colors: List<Color>
        get() = feelings
            .sortedBy { it.emotion().order }
            .map { it.color() }

    fun matches(contains: String = "", emotions: List<Emotion> = emptyList()): Boolean
    fun dateString(): String

    companion object {
        fun random() = object : HistoryRecord {
            override val text = randomString()
            override val feelings = listOf(Feeling.random())
            override fun matches(contains: String, emotions: List<Emotion>) = true
            override fun dateString() = "random date"
        }
    }
}
