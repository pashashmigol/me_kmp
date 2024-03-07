package model

import data.storage.utils.FeelingsListSerializer
import data.storage.utils.LocalDateTimeSerializer
import data.utils.randomDate
import data.utils.randomString
import data.utils.format
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

const val dateTimeFormat = "EEE, dd MMM yyyy HH:mm"

@Serializable
data class MoodRecord(
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime,
    override val text: String = "",
    @Serializable(with = FeelingsListSerializer::class)
    override val feelings: List<Feeling> = listOf()
) : HistoryRecord {

    override fun dateString() = date.format(dateTimeFormat)

    override fun matches(contains: String, emotions: List<Emotion>): Boolean {
        val desiredFeelings = emotions.flatMap { emotion -> emotion.feelings }

        val hasDesiredEmotion = desiredFeelings.isEmpty() ||
                feelings.any { it: Feeling -> it in desiredFeelings }

        val hasDesiredWords = contains.isBlank() ||
                contains.split(" ")
                    .filter { it.isNotBlank() }
                    .any { text.contains(it.trim()) }

        return hasDesiredEmotion && hasDesiredWords
    }

    companion object {
        const val MAX_TEXT_LENGTH = 140
        const val MIN_TEXT_LENGTH = 0

        const val MIN_EMOTIONS_NUMBER = 1
        const val MAX_EMOTIONS_NUMBER = 1

        fun random(): MoodRecord = MoodRecord(
            date = randomDate(),
            text = randomString(),
            feelings = listOf(Feeling.random())
        )
    }
}