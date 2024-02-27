package model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class HashTag(
    val value: String,
    val lastUsed: LocalDateTime,
) {
    companion object
}
