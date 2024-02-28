package data.utils

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Clock.System.now
import kotlin.random.Random.Default.nextInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

fun randomDate(): LocalDateTime = LocalDateTime(
    year = nextInt(2010, 2024),
    monthNumber = nextInt(1, 13),
    dayOfMonth = nextInt(1, 32),
    hour = nextInt(0, 23),
    minute = nextInt(0, 59),
    second = nextInt(0, 59),
)

expect fun LocalDateTime.format(dateTimeFormat: String): String

expect fun String.toLocalDateTime(dateTimeFormat: String): LocalDateTime

val LocalDateTime.startOfMonth: LocalDateTime
    get() = LocalDateTime(
        year = year,
        monthNumber = monthNumber,
        dayOfMonth = 1,
        hour = 0,
        minute = 0
    )

val LocalDateTime.endOfMonth: LocalDateTime
    get() = LocalDateTime(
        year = year,
        month = month,
        dayOfMonth = dayOfMonth,
        hour = 23,
        minute = 59,
        second = 59,
        nanosecond = 999
    )

val LocalDateTime.startOfIsoWeek: LocalDateTime
    get() {
        for (n in 0 until 7) {
            val date = (this - n.days)
            if (date.dayOfWeek == DayOfWeek.MONDAY) return date.startOfDay
        }
        error("Shouldn't happen")
    }

val LocalDateTime.endOfIsoWeek: LocalDateTime
    get() {
        for (n in 0 until 7) {
            val date = (this + n.days)
            if (date.dayOfWeek == DayOfWeek.SUNDAY) return date.endOfDay
        }
        error("Shouldn't happen")
    }

operator fun LocalDateTime.minus(delta: Duration): LocalDateTime =
    toInstant(UtcOffset.ZERO)
        .minus(delta)
        .toLocalDateTime(TimeZone.currentSystemDefault())

operator fun LocalDateTime.plus(delta: Duration): LocalDateTime =
    toInstant(UtcOffset.ZERO)
        .plus(delta)
        .toLocalDateTime(TimeZone.currentSystemDefault())

val LocalDateTime.startOfDay: LocalDateTime
    get() = LocalDateTime(
        year = this.year,
        month = this.month,
        dayOfMonth = this.dayOfMonth,
        hour = 0,
        minute = 0
    )

val LocalDateTime.endOfDay
    get() = LocalDateTime(
        year = year,
        month = month,
        dayOfMonth = dayOfMonth,
        hour = 23,
        minute = 59,
        second = 59,
        nanosecond = 999
    )

fun now(): LocalDateTime = now().toLocalDateTime(TimeZone.currentSystemDefault())

fun date(year: Int, month: Int, dayOfMonth: Int) = LocalDateTime(
    year = year,
    monthNumber = month,
    dayOfMonth = dayOfMonth,
    hour = 0,
    minute = 0
)
