package com.me.screens.history.viewmodels

import com.me.data.Repository
import com.me.data.utils.filteredRecords
import com.me.model.DayRecord
import com.me.model.HistoryRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class DaysViewModel @Inject constructor(repo: Repository) : HistoryViewModelReal<DayRecord>(repo) {
    override val records: Flow<List<HistoryRecord>>
        get() = repo.days
            .combine(filter) { records: List<DayRecord>, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records,
                    filter = filter,
                )
            }
}
