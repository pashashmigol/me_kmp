package com.me.screens.history.viewmodels

import com.me.model.HistoryRecord
import com.me.model.MoodRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HistoryViewModelStub : HistoryViewModel<HistoryRecord>() {
    override fun addRecord(moodRecord: MoodRecord) {}
    override val records: StateFlow<List<HistoryRecord>> = MutableStateFlow(
        listOf(HistoryRecord.random())
    )
}
