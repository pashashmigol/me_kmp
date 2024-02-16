package com.me.screens.history.viewmodels

import data.Repository
import data.utils.filteredRecords
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import model.HistoryRecord
import model.MonthRecord
import model.MoodRecord
import screens.history.viewmodels.Filter
import screens.history.viewmodels.HistoryViewModelReal

class OneMonthRecordsViewModel(repo: Repository) :
    HistoryViewModelReal<MoodRecord>(repo) {
    var monthIndex: Int? = null

    override val records: Flow<List<HistoryRecord>>
        get() = repo.months
            .map { it[monthIndex!!] }
            .combine(filter) { records: MonthRecord, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records.records,
                    filter = filter,
                )
            }
}
