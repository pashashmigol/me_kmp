package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import model.MonthRecord

class MonthsViewModel(repo: Repository) :
    HistoryViewModelReal<MonthRecord>(repo) {

    override val records: StateFlow<List<MonthRecord>> = repo.months
            .combine(filter) { records: List<MonthRecord>, filter: RecordsFilter ->
                filteredRecords(
                    unfilteredRecords = records,
                    filter = filter,
                )
            }
            .stateIn(
                scope = scope,
                started = SharingStarted.Lazily,
                initialValue = emptyList()
            )
}
