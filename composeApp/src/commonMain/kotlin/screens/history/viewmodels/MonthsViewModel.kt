package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import model.HistoryRecord
import model.MonthRecord

class MonthsViewModel(repo: Repository) :
    HistoryViewModelReal<MonthRecord>(repo) {

    override val records: Flow<List<HistoryRecord>>
        get() = repo.months
            .combine(filter) { records: List<MonthRecord>, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records,
                    filter = filter,
                )
            }
}
