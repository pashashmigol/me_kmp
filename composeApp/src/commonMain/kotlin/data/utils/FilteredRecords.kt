package com.me.data.utils

import com.me.model.HistoryRecord
import com.me.screens.history.viewmodels.Filter

fun <T : HistoryRecord> filteredRecords(
    unfilteredRecords: List<T>,
    filter: Filter,
): List<T> {
    return unfilteredRecords.filter { record: T ->
        record.matches(
            contains = filter.text.trim(),
            emotions = filter.emotions
        )
    }
}