package com.me.model

import korlibs.time.Date
import model.CompositeRecord

class WeekRecord(index: Int, start: Date, end: Date, records: List<MoodRecord>) :
    CompositeRecord(index, start, end, records) {

    override fun dateString() = if (start.month == end.month) {
        "${start.year}, " +
                "${start.month.localShortName} ${start.day}-${end.day}"
    } else {
        "${start.year}, " +
                "${start.month.localShortName} ${start.day} - " +
                "${end.month.localShortName} ${end.day}"
    }

    override val text = "${records.size} records"
}