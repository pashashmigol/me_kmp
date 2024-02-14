package model

import data.utils.format
import kotlinx.datetime.LocalDateTime

class DayRecord(index: Int, start: LocalDateTime, records: List<MoodRecord>) :
    CompositeRecord(index, start, start, records) {

    override fun dateString() = start.format(dateFormat)
    override val text = "${records.size} records"
}