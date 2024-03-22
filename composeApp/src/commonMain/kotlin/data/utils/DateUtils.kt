package data.utils

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlin.random.Random.Default.nextInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

const val DATE_TIME_FORMAT_OLD = "EEE, dd MMM yyyy HH:mm:ss 'GMT'Z"
const val DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm:ss"
const val DATE_FORMAT = "EEE, dd MMM yyyy"

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

expect fun String.toLocalDateTime(): LocalDateTime

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
    toInstant(TimeZone.UTC)
        .minus(delta)
        .toLocalDateTime(TimeZone.UTC)

operator fun LocalDateTime.plus(delta: Duration): LocalDateTime =
    toInstant(TimeZone.UTC)
        .plus(delta)
        .toLocalDateTime(TimeZone.UTC)

val LocalDateTime.startOfDay: LocalDateTime
    get() = LocalDateTime(
        year = this.year,
        month = this.month,
        dayOfMonth = this.dayOfMonth,
        hour = 0,
        minute = 0
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
