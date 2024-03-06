package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import model.HistoryRecord
import model.MoodRecord

class TodayRecordsViewModel (repo: Repository) :
    HistoryViewModelReal<MoodRecord>(repo) {

    override val records: Flow<List<HistoryRecord>>
        get() = repo.todayRecords
            .combine(filter) { records: List<MoodRecord>, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records,
                    filter = filter,
                )
            }
}
