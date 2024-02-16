package model

import kotlinx.datetime.LocalDateTime


class WeekRecord(index: Int, start: LocalDateTime, end: LocalDateTime, records: List<MoodRecord>) :
    CompositeRecord(index, start, end, records) {

    override fun dateString() = if (start.month == end.month) {
        "${start.year}, " +
                "${start.month.name} ${start.dayOfMonth}-${end.dayOfMonth}"
    } else {
        "${start.year}, " +
                "${start.month.name} ${start.dayOfMonth} - " +
                "${end.month.name} ${end.dayOfMonth}"
    }

    override val text = "${records.size} records"
}