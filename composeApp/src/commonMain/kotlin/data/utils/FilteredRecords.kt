package data.utils

import model.HistoryRecord
import screens.history.viewmodels.Filter

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