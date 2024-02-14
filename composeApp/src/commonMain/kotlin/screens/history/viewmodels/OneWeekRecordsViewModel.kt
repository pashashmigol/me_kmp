package com.me.screens.history.viewmodels

import com.me.data.Repository
import com.me.data.utils.filteredRecords
import com.me.model.HistoryRecord
import com.me.model.MoodRecord
import com.me.model.WeekRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class OneWeekRecordsViewModel @Inject constructor(repo: Repository) :
    HistoryViewModelReal<MoodRecord>(repo) {
    var weekIndex: Int? = null

    override val records: Flow<List<HistoryRecord>>
        get() = repo.weeks
            .map { it[weekIndex!!] }
            .combine(filter) { records: WeekRecord, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records.records,
                    filter = filter,
                )
            }
}