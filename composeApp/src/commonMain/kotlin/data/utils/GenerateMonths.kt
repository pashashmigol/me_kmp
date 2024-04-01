package data.utils

import kotlinx.datetime.LocalDateTime
import model.MonthRecord
import model.MoodRecord

fun generateMonths(records: List<MoodRecord>): List<MonthRecord> = records
    .fold(initial = mutableMapOf<LocalDateTime, MutableList<MoodRecord>>()) { acc, moodRecord ->
        val monthsStart = moodRecord.date.startOfMonth

        if (!acc.containsKey(monthsStart)) {
            acc[monthsStart] = mutableListOf()
        }
        acc[monthsStart]!! += moodRecord
        acc
    }
    .entries
    .sortedBy { it.key }
    .foldIndexed(
        initial = mutableListOf()
    )
    { index, acc, monthsRecords ->
        MonthRecord(
            index = index,
            start = monthsRecords.key.date,
            end = monthsRecords.key.date.endOfMonth,
            records = monthsRecords.value.toList()

        ).let { acc.add(it) }
        acc
    }
