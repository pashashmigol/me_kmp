package com.me.screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import model.DayRecord
import model.HistoryRecord
import model.MoodRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import screens.history.viewmodels.Filter
import screens.history.viewmodels.HistoryViewModelReal

class OneDayRecordsViewModel (repo: Repository) :
    HistoryViewModelReal<MoodRecord>(repo) { var dayIndex: Int? = null

    override val records: Flow<List<HistoryRecord>>
        get() = repo.days
            .map { it[dayIndex!!] }
            .combine(filter) { records: DayRecord, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records.records,
                    filter = filter,
                )
            }
}
