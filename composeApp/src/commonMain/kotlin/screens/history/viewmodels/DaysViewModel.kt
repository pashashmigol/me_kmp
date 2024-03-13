package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.DayRecord

class DaysViewModel(repo: Repository) : HistoryViewModelReal<DayRecord>(repo) {
    init {
        scope.launch(Dispatchers.IO) {
            val state = repo.days
                .filter {
                    it.isNotEmpty()
                }
                .combine(filter) { records: List<DayRecord>, filter: Filter ->
                    filteredRecords(
                        unfilteredRecords = records,
                        filter = filter,
                    )
                }.onEach {
                    records.clear()
                    records.addAll(it)
                }.stateIn(scope)

            records.clear()
            records.addAll(state.value)
        }
    }
}
