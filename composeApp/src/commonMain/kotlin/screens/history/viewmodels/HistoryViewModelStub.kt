package screens.history.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.HistoryRecord
import model.MoodRecord

class HistoryViewModelStub : HistoryViewModel<HistoryRecord>() {
    override fun addRecord(moodRecord: MoodRecord) {}
    override val records: StateFlow<List<HistoryRecord>> = MutableStateFlow(
        listOf(HistoryRecord.random())
    )
}
