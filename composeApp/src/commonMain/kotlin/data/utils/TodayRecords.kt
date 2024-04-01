package data.utils

import model.MoodRecord

fun todayRecords(records: List<MoodRecord>) = records.filter {
    it.date.date == lazy { now().date }.value
}.sortedBy { it.date }
