package data.utils

import data.storage.FORMAT
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual fun String.toLocalDateTime(dateTimeFormat: String): LocalDateTime {
    val formatter = NSDateFormatter().apply {
        dateFormat = FORMAT
        locale = NSLocale.currentLocale
    }
    return formatter
        .dateFromString(this)
        ?.toKotlinInstant()
        ?.toLocalDateTime(TimeZone.of("EET"))
        ?: throw IllegalStateException("Failed to convert String $this to LocalDateTime")
}
