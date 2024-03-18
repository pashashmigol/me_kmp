package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import model.MonthRecord
import model.MoodRecord

class OneMonthRecordsViewModel(repo: Repository) :
    HistoryViewModelReal<MoodRecord>(repo) {
    var monthIndex: Int? = null

    override val records
        get() = repo.months
            .map { days: List<MonthRecord> ->
                monthIndex
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
