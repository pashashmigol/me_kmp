package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import model.MoodRecord
import model.WeekRecord

class OneWeekRecordsViewModel(repo: Repository, val weekIndex: Int? = null) :
    HistoryViewModelReal<MoodRecord>(repo) {

    override val records
        get() = repo.weeks
            .map { days: List<WeekRecord> ->
                weekIndex
                    ?.takeIf { it < days.size }
                    ?.let { days[it].records }
            }
            .filterNotNull()
            .combine(filter) { records: List<MoodRecord>, filter: Filter ->
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
