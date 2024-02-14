package com.me.screens.history.viewmodels

import com.me.data.Repository
import com.me.data.utils.filteredRecords
import com.me.model.HistoryRecord
import com.me.model.MonthRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class MonthsViewModel @Inject constructor(repo: Repository) :
    HistoryViewModelReal<MonthRecord>(repo) {

    override val records: Flow<List<HistoryRecord>>
        get() = repo.months
            .combine(filter) { records: List<MonthRecord>, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records,
                    filter = filter,
                )
            }
}
