package data.utils

import model.HistoryRecord
import screens.history.viewmodels.RecordsFilter

fun <T : HistoryRecord> filteredRecords(
    unfilteredRecords: List<T>,
    filter: RecordsFilter,
): List<T> {
    return unfilteredRecords.filter { record: T ->
        record.matches(
            contains = filter.text.trim(),
            emotions = filter.emotions
        )
    }
}