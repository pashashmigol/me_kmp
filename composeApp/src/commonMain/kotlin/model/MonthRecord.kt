package com.me.model

import korlibs.time.Date
import model.CompositeRecord

class MonthRecord(index: Int, start: Date, end: Date, records: List<MoodRecord>) :
    CompositeRecord(index, start, end, records) {

    override fun dateString() = "${start.year}, ${start.month.localShortName}"
    override val text = "${records.size} records"
}