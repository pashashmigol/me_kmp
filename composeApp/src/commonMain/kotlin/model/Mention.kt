package model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


data class Mention(
    val value: String,
    val lastUsed: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
) {
    companion object
}
