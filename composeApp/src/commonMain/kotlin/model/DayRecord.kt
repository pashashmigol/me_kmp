package model

import data.utils.format
import kotlinx.datetime.LocalDate

data class DayRecord(
    override val index: Int,
    override val start: LocalDate,
    override val records: List<MoodRecord>
) :
    CompositeRecord(index, start, start, records) {

    override fun dateString() = start.format(dateFormat)
    override val text = "${records.size} records"
}