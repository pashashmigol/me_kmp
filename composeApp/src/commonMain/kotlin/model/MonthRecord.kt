package model

import kotlinx.datetime.LocalDate

data class MonthRecord(
    override val index: Int,
    override val start: LocalDate,
    override val end: LocalDate,
    override val records: List<MoodRecord>
) : CompositeRecord(index, start, end, records) {

    override fun dateString() = "${start.year}, ${start.month.name}"
    override val text = "${records.size} records"
}