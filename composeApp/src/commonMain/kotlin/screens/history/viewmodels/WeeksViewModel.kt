package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import model.HistoryRecord
import model.MoodRecord
import model.WeekRecord

class WeeksViewModel(repo: Repository) :
    HistoryViewModelReal<WeekRecord>(repo) {

    init {
        repo.weeks
            .combine(filter) { records: List<WeekRecord>, filter: Filter ->
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
//            .combine(filter) { records: List<WeekRecord>, filter: Filter ->
//                filteredRecords(
//                    unfilteredRecords = records,
//                    filter = filter,
//                )
//            }
}
