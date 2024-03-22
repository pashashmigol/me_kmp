package data.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
actual fun LocalDate.format(dateFormat: String): String {
    val javaDateTime = toJavaLocalDate()

    return DateTimeFormatter
        .ofPattern(dateFormat, Locale.getDefault())
        .format(javaDateTime)
}

@RequiresApi(Build.VERSION_CODES.O)
actual fun LocalDateTime.format(dateTimeFormat: String): String {
//    val timeZone = TimeZone.currentSystemDefault()
//    val offset = Clock.System.now().offsetIn(timeZone).toJavaZoneOffset()
    val javaDateTime = toJavaLocalDateTime()
        .toInstant(ZoneOffset.UTC)
        .atOffset(ZoneOffset.UTC)

    return DateTimeFormatter
        .ofPattern(dateTimeFormat, Locale.getDefault())
        .format(javaDateTime)
}

@SuppressLint("ConstantLocale")
@RequiresApi(Build.VERSION_CODES.O)
val dateFormat: DateTimeFormatter = DateTimeFormatter
    .ofPattern(DATE_TIME_FORMAT, Locale.getDefault())


@SuppressLint("ConstantLocale")
@RequiresApi(Build.VERSION_CODES.O)
val dateFormatOld: DateTimeFormatter = DateTimeFormatter
    .ofPattern(DATE_TIME_FORMAT_OLD, Locale.getDefault())

@RequiresApi(Build.VERSION_CODES.O)
actual fun String.toLocalDateTime(): LocalDateTime = runCatching {
    java.time.LocalDateTime.parse(this, dateFormat)
}.getOrElse {
    java.time.LocalDateTime.parse(this, dateFormatOld)
}
    .atZone(ZoneOffset.UTC)
    .toInstant()
    .toKotlinInstant()
    .toLocalDateTime(TimeZone.UTC)
