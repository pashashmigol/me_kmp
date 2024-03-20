package model

import data.utils.format
import kotlinx.datetime.LocalDate

class DayRecord(index: Int, start: LocalDate, records: List<MoodRecord>) :
    CompositeRecord(index, start, start, records) {

    override fun dateString() = start.format(dateFormat)
    override val text = "${records.size} records"
}