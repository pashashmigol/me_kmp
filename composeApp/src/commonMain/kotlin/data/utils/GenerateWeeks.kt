package data.utils

import kotlinx.datetime.LocalDateTime
import model.MoodRecord
import model.WeekRecord

fun generateWeeks(records: List<MoodRecord>): List<WeekRecord> = records
    .fold(initial = mutableMapOf<LocalDateTime, MutableList<MoodRecord>>()) { acc, moodRecord ->
        val weekStart = moodRecord.date.startOfIsoWeek

        if (!acc.containsKey(weekStart)) {
            acc[weekStart] = mutableListOf()
        }
        acc[weekStart]!! += moodRecord
        acc
    }.entries
    .foldIndexed<MutableMap.MutableEntry<LocalDateTime, MutableList<MoodRecord>>, MutableList<WeekRecord>>(initial = mutableListOf()) { index, acc, weekRecords ->
        WeekRecord(
            index = index,
            start = weekRecords.key.date,
            end = weekRecords.key.date.endOfIsoWeek,
            records = weekRecords.value.toList()
        ).let { acc.add(it) }
        acc
    }.sortedBy { it.start }
