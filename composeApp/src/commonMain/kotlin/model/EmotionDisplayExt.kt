package com.me.model

import androidx.compose.ui.graphics.Color

fun Emotion.color(): Color {
    return when (this) {
        is Anger -> Color(163, 42, 34)
        is Fear -> Color(219, 85, 56)
        is Sad -> Color(238, 181, 68)
        is Happy -> Color(82, 165, 84)
        is Surprise -> Color(55, 123, 159)
        is Peaceful -> Color(95, 104, 148)
    }
}

val Emotion.order: Int
    get() {
        return when (this) {
            is Anger -> 0
            is Fear -> 1
            is Sad -> 2
            is Happy -> 3
            is Surprise -> 4
            is Peaceful -> 5
        }
    }