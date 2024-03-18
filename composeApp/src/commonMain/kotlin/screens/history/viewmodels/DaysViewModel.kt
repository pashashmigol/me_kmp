package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import model.DayRecord
import model.HistoryRecord

class DaysViewModel(repo: Repository) : HistoryViewModelReal<DayRecord>(repo) {
    override val records: StateFlow<List<HistoryRecord>>
        get() {
            return repo.days
                .combine(filter) { records: List<DayRecord>, filter: Filter ->
                    println("### DaysViewModel; unfilters records ${records.size}")
                    val filtered = filteredRecords(
                        unfilteredRecords = records,
                        filter = filter,
                    )
                    println("### DaysViewModel; filters records ${filtered.size}")
                    filtered
                }.stateIn(
                    scope = scope,
                    started = SharingStarted.Lazily,
                    initialValue = emptyList()
                )
        }
}
