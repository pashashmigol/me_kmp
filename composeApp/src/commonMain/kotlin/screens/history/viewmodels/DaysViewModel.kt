package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import model.DayRecord
import model.HistoryRecord

class DaysViewModel(repo: Repository) : HistoryViewModelReal<DayRecord>(repo) {
    override val records: Flow<List<HistoryRecord>>
        get() = repo.days
            .combine(filter) { records: List<DayRecord>, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records,
                    filter = filter,
                )
            }
}
