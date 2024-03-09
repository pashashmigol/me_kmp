package screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import model.DayRecord
import model.HistoryRecord
import model.MonthRecord

class MonthsViewModel(repo: Repository) :
    HistoryViewModelReal<MonthRecord>(repo) {

    init {
        repo.months
            .filter { it.isNotEmpty() }
            .combine(filter) { records: List<MonthRecord>, filter: Filter ->
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
//        get() = repo.months
//            .combine(filter) { records: List<MonthRecord>, filter: Filter ->
//                filteredRecords(
//                    unfilteredRecords = records,
//                    filter = filter,
//                )
//            }
}
