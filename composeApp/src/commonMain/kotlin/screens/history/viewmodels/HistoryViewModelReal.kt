package com.me.screens.history.viewmodels

import com.me.data.Repository
import com.me.model.HistoryRecord
import com.me.model.MoodRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

abstract class HistoryViewModelReal<T : HistoryRecord>(val repo: Repository) :
    HistoryViewModel<T>() {

    internal val scope = CoroutineScope(Dispatchers.Main)

    override fun addRecord(moodRecord: MoodRecord) {
        repo.addRecord(moodRecord)
    }
}
