package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import model.WeekRecord

class WeeksViewModel(repo: Repository) :
    HistoryViewModelReal<WeekRecord>(repo) {

    override val records
        get() = repo.weeks
            .combine(filter) { records: List<WeekRecord>, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records,
                    filter = filter,
                )
            }.stateIn(
                scope = scope,
                started = SharingStarted.Lazily,
                initialValue = emptyList()
            )
}
