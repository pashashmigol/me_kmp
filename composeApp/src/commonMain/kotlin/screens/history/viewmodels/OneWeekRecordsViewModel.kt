package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import model.HistoryRecord
import model.MonthRecord
import model.MoodRecord
import model.WeekRecord

class OneWeekRecordsViewModel(repo: Repository) :
    HistoryViewModelReal<MoodRecord>(repo) {
    var weekIndex: Int? = null

    init {
        repo.weeks
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
            }.onEach {
                records.clear()
                records.addAll(records)
            }
    }

//    override val records: Flow<List<HistoryRecord>>
//        get() = repo.weeks
//            .map { days: List<WeekRecord> ->
//                weekIndex
//                    ?.takeIf { it < days.size }
//                    ?.let { days[it].records }
//            }
//            .filterNotNull()
//            .combine(filter) { records: List<MoodRecord>, filter: Filter ->
//                filteredRecords(
//                    unfilteredRecords = records,
//                    filter = filter,
//                )
//            }
}
