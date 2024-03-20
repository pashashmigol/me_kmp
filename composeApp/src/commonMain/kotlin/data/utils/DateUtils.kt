package data.utils

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlin.random.Random.Default.nextInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

fun randomDate(): LocalDateTime = LocalDateTime(
    year = nextInt(2010, 2024),
    monthNumber = nextInt(1, 13),
    dayOfMonth = nextInt(1, 29),
    hour = nextInt(0, 23),
    minute = nextInt(0, 59),
    second = nextInt(0, 59),
)

expect fun LocalDateTime.format(dateTimeFormat: String): String

expect fun LocalDate.format(dateFormat: String): String

expect fun String.toLocalDateTime(dateTimeFormat: String): LocalDateTime

val LocalDateTime.startOfMonth: LocalDateTime
    get() = LocalDateTime(
        year = year,
        monthNumber = monthNumber,
        dayOfMonth = 1,
        hour = 0,
        minute = 0
    )

val LocalDate.endOfMonth: LocalDate
    get() {
        return LocalDate(
            year = year,
            month = month,
            dayOfMonth = YearMonth(year, month).atEndOfMonth().dayOfMonth,
        )
    }

val LocalDateTime.startOfIsoWeek: LocalDateTime
    get() {
        for (n in 0 until 7) {
            val date = (this - n.days)
            if (date.dayOfWeek == DayOfWeek.MONDAY) return date.startOfDay
        }
        error("Shouldn't happen")
    }

val LocalDate.endOfIsoWeek: LocalDate
    get() {
        for (n in 0 until 7) {
            val date = (this + DatePeriod(days = n))
            if (date.dayOfWeek == DayOfWeek.SUNDAY) return date
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
fun today(): LocalDate = now().toLocalDateTime(TimeZone.currentSystemDefault()).date

fun dateTime(year: Int, month: Int, dayOfMonth: Int) = LocalDateTime(
    year = year,
    monthNumber = month,
    dayOfMonth = dayOfMonth,
    hour = 0,
    minute = 0
)

fun date(year: Int, month: Int, dayOfMonth: Int) = LocalDate(
    year = year,
    monthNumber = month,
    dayOfMonth = dayOfMonth
)
