package data.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetIn
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaZoneOffset
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
actual fun LocalDateTime.format(dateTimeFormat: String): String {
    val timeZone = TimeZone.currentSystemDefault()
    val offset = Clock.System.now().offsetIn(timeZone).toJavaZoneOffset()
    val javaDateTime: OffsetDateTime = toJavaLocalDateTime()
        .atOffset(offset)

    TimeZone.currentSystemDefault()
    return DateTimeFormatter
        .ofPattern(dateTimeFormat, Locale.getDefault())
        .format(javaDateTime)
}

@RequiresApi(Build.VERSION_CODES.O)
actual fun String.toLocalDateTime(dateTimeFormat: String): LocalDateTime {
    val dateFormat = DateTimeFormatter
        .ofPattern(dateTimeFormat, Locale.getDefault())

    return java.time.LocalDateTime
        .parse(this, dateFormat)
        .toKotlinLocalDateTime()
}
