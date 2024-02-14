package com.me.screens.history.viewmodels

import androidx.lifecycle.viewModelScope
import com.me.data.Repository
import com.me.data.utils.filteredRecords
import com.me.model.HistoryRecord
import com.me.model.MoodRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class TodayRecordsViewModel @Inject constructor(repo: Repository) :
    HistoryViewModelReal<MoodRecord>(repo) {

    override val records: Flow<List<HistoryRecord>>
        get() = repo.todayRecords
            .combine(filter) { records: List<MoodRecord>, filter: Filter ->
                filteredRecords(
                    unfilteredRecords = records,
                    filter = filter,
                )
            }
}
