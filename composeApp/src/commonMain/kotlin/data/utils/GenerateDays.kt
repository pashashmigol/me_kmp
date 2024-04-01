package data.utils

import kotlinx.datetime.LocalDateTime
import model.DayRecord
import model.MoodRecord

fun generateDays(records: List<MoodRecord>): List<DayRecord> = records
    .fold(
        initial = mutableMapOf<LocalDateTime, MutableList<MoodRecord>>()
    ) { acc, moodRecord ->
        val startOfDay = moodRecord.date.startOfDay

        if (!acc.containsKey(startOfDay)) {
            acc[startOfDay] = mutableListOf()
        }
        acc[startOfDay]!! += moodRecord
        acc
    }.entries.foldIndexed<MutableMap.MutableEntry<LocalDateTime, MutableList<MoodRecord>>, MutableList<DayRecord>>(
        initial = mutableListOf()
    ) { index, acc, dayRecords ->
        DayRecord(
            index = index,
            start = dayRecords.key.date,
            records = dayRecords.value.toList()
        ).let { acc.add(it) }
        acc
    }.sortedBy { it.start }
