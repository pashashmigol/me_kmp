package com.me.screens.history.viewmodels

import com.me.data.Repository
import com.me.data.utils.filteredRecords
import com.me.model.HistoryRecord
import com.me.model.WeekRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WeekViewModel @Inject constructor(repo: Repository) :
    HistoryViewModelReal<WeekRecord>(repo) {

    override val records: Flow<List<HistoryRecord>>
        get() = repo.weeks
            .combine(filter) { records: List<WeekRecord>, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records,
                    filter = filter,
                )
            }
}
