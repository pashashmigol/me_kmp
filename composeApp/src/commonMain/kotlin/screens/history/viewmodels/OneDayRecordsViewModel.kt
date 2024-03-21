package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import model.DayRecord
import model.MoodRecord
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class OneDayRecordsViewModel(repo: Repository, val dayIndex: Int?) : HistoryViewModelReal<MoodRecord>(repo) {
//    var dayIndex: Int? = null

    override val records
        get() = repo.days
            .map { days: List<DayRecord> ->
                dayIndex
                    ?.takeIf { it < days.size }
                    ?.let { days[it].records }
            }
            .filterNotNull()
            .combine(filter) { records: List<MoodRecord>, filter: RecordsFilter ->
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
