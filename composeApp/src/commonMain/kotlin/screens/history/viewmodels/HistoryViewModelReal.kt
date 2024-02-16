package screens.history.viewmodels

import data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import model.HistoryRecord
import model.MoodRecord

abstract class HistoryViewModelReal<T : HistoryRecord>(val repo: Repository) :
    HistoryViewModel<T>() {

    internal val scope = CoroutineScope(Dispatchers.Main)

    override fun addRecord(moodRecord: MoodRecord) {
        repo.addRecord(moodRecord)
    }
}
