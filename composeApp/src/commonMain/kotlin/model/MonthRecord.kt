package model

import kotlinx.datetime.LocalDateTime

class MonthRecord(
    index: Int,
    start: LocalDateTime,
    end: LocalDateTime,
    records: List<MoodRecord>
) :
    CompositeRecord(index, start, end, records) {

    override fun dateString() = "${start.year}, ${start.month.name}"
    override val text = "${records.size} records"
}