package com.me.screens.history.viewmodels

import com.me.data.Repository
import com.me.data.utils.filteredRecords
import com.me.model.HistoryRecord
import com.me.model.MonthRecord
import com.me.model.MoodRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class OneMonthRecordsViewModel @Inject constructor(repo: Repository) :
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
