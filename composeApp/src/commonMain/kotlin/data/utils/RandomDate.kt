package data.utils

import kotlinx.datetime.LocalDateTime
import kotlin.random.Random.Default.nextInt

fun randomDate(): LocalDateTime = LocalDateTime(
    year = nextInt(2010, 2024),
    monthNumber = nextInt(1, 13),
    dayOfMonth =  nextInt(1, 32),
    hour = nextInt(0, 23),
    minute = nextInt(0, 59),
    second = nextInt(0, 59),
)

expect fun LocalDateTime.format(dateTimeFormat: String): String
