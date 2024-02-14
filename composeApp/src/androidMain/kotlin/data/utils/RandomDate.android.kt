package data.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
actual fun LocalDateTime.format(dateTimeFormat: String): String {
    return DateTimeFormatter
        .ofPattern(dateTimeFormat, Locale.getDefault())
        .format(this.toJavaLocalDateTime())
}
