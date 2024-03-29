package model

import kotlinx.datetime.LocalDate

sealed class CompositeRecord(
    open val index: Int,
    open val start: LocalDate,
    open val end: LocalDate,
    open val records: List<MoodRecord>
) : HistoryRecord {
    override val feelings: List<Feeling>
        get() = records
            .map { it.feelings }
            .flatten()
            .sortedBy {
                Emotion.values.indexOf(it.emotion())
            }

    override fun matches(contains: String, emotions: List<Emotion>): Boolean {
        return records.any { it.matches(contains, emotions) }
    }

    fun matchedRecords(contains: String, emotions: List<Emotion>): List<MoodRecord> {
        return records.filter { it.matches(contains, emotions) }
    }

    val mainEmotions: List<Feeling>
        get() = records.flatMap {
            it.feelings
        }.fold(mutableMapOf<Feeling, MutableList<Feeling>>()) { acc, emotion ->
            if (!acc.containsKey(emotion)) {
                acc[emotion] = mutableListOf()
            }
            acc[emotion]!! += emotion
            acc
        }.values
            .sortedByDescending { it.size }
            .flatten()
            .take(3)
}
