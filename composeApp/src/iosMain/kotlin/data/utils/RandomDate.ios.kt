package data.utils

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.convert
import kotlinx.datetime.LocalDateTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

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


//actual fun LocalDateTime.format(localDateTime: LocalDateTime): String {
actual fun LocalDateTime.format(dateTimeFormat: String): String {
    val date = toNsDate() ?: throw IllegalStateException(
        "Failed to convert LocalDateTime $LocalDateTime to NSDate"
    )
    val formatter = NSDateFormatter().apply {
        dateFormat = dateTimeFormat
        locale = NSLocale.currentLocale
    }
    return formatter.stringFromDate(date) // <-
}