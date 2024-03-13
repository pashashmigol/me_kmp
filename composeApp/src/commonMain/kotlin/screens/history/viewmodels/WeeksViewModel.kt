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
import model.WeekRecord

class WeeksViewModel(repo: Repository) :
    HistoryViewModelReal<WeekRecord>(repo) {

    init {
        scope.launch(Dispatchers.IO) {
            val state = repo.weeks
                .filter {
                    it.isNotEmpty()
                }
                .combine(filter) { records: List<WeekRecord>, filter: Filter ->
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
