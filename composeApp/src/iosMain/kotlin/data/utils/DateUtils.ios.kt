package data.utils

import data.storage.DATE_TIME_FORMAT
import data.storage.DATE_FORMAT
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.toNSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

private val dateTimeFormatter = NSDateFormatter().apply {
    dateFormat = DATE_TIME_FORMAT
    locale = NSLocale.currentLocale
}

private val dateFormatter = NSDateFormatter().apply {
    dateFormat = DATE_FORMAT
    locale = NSLocale.currentLocale
}

actual fun String.toLocalDateTime(dateTimeFormat: String): LocalDateTime {

    return dateTimeFormatter
        .dateFromString(this)
        ?.toKotlinInstant()
        ?.toLocalDateTime(TimeZone.currentSystemDefault())
        ?: throw IllegalStateException("Failed to convert String $this to LocalDateTime")
}

actual fun LocalDate.format(dateFormat: String): String {
    return toNSDateComponents().date()?.let {
        dateFormatter.stringFromDate(it)
    } ?: "format error"
}