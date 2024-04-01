package screens.history.viewmodels

import com.rickclephas.kmm.viewmodel.coroutineScope
import data.Repository
import kotlinx.coroutines.CoroutineScope
import model.HistoryRecord
import model.MoodRecord

abstract class HistoryViewModelReal<T : HistoryRecord>(val repo: Repository) :
    HistoryViewModel<T>() {

    internal val scope: CoroutineScope = viewModelScope.coroutineScope

    override fun addRecord(moodRecord: MoodRecord) {
        println("### HistoryViewModelReal.addRecord(): $moodRecord")
        repo.addRecord(moodRecord)
    }
}
