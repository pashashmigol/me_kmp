package data.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toNSDateComponents
import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

private val dateTimeFormatterOld = NSDateFormatter().apply {
    dateFormat = DATE_TIME_FORMAT_OLD
    locale = NSLocale.currentLocale
}

private val dateTimeFormatter = NSDateFormatter().apply {
    dateFormat = DATE_TIME_FORMAT
    locale = NSLocale.currentLocale
}

private val dateFormatter = NSDateFormatter().apply {
    dateFormat = DATE_FORMAT
    locale = NSLocale.currentLocale
}

actual fun String.toLocalDateTime(): LocalDateTime {
    return dateTimeFormatter.dateFromString(this)
        ?.toKotlinInstant()
        ?.toLocalDateTime(TimeZone.currentSystemDefault())
        ?: dateTimeFormatterOld.dateFromString(this)
            ?.toKotlinInstant()
            ?.toLocalDateTime(TimeZone.currentSystemDefault())
        ?: throw IllegalStateException("Failed to convert String $this to LocalDateTime")
}

actual fun LocalDate.format(dateFormat: String): String {
    return toNSDateComponents().date()?.let {
        dateFormatter.stringFromDate(it)
    } ?: "format error"
}

@OptIn(ExperimentalForeignApi::class)
private fun LocalDateTime.toNsDate(): NSDate? {
    val calendar = NSCalendar.currentCalendar
    val components = NSDateComponents()
    components.year = year.convert()
    components.month = monthNumber.convert()
    components.day = dayOfMonth.convert()
    components.hour = hour.convert()
    components.minute = minute.convert()
    components.second = second.convert()
    return calendar.dateFromComponents(components)
}

actual fun LocalDateTime.format(dateTimeFormat: String): String {
    val date = toNsDate() ?: throw IllegalStateException(
        "Failed to convert LocalDateTime $LocalDateTime to NSDate"
    )
    val formatter = NSDateFormatter().apply {
        dateFormat = dateTimeFormat
        locale = NSLocale.currentLocale
    }
    return formatter.stringFromDate(date)
}