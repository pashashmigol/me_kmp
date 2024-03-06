package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import model.DayRecord
import model.HistoryRecord
import model.MoodRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class OneDayRecordsViewModel(repo: Repository) :
    HistoryViewModelReal<MoodRecord>(repo) {
    var dayIndex: Int? = null

    override val records: Flow<List<HistoryRecord>>
        get() = repo.days
            .map { days: List<DayRecord> ->
                dayIndex
                    ?.takeIf { it < days.size }
                    ?.let { days[it].records }
            }
            .filterNotNull()
            .combine(filter) { records: List<MoodRecord>, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records,
                    filter = filter,
                )
            }
}
