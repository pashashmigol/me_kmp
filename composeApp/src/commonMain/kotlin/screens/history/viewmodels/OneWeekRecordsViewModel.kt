package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import model.HistoryRecord
import model.MoodRecord
import model.WeekRecord

class OneWeekRecordsViewModel  (repo: Repository) :
    HistoryViewModelReal<MoodRecord>(repo) {
    var weekIndex: Int? = null

    override val records: Flow<List<HistoryRecord>>
        get() = repo.weeks
            .map { it[weekIndex!!] }
            .combine(filter) { records: WeekRecord, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records.records,
                    filter = filter,
                )
            }
}
