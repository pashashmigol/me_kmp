package screens.history.viewmodels

import androidx.compose.ui.text.input.TextFieldValue
import com.rickclephas.kmm.viewmodel.KMMViewModel
import model.HistoryRecord
import screens.history.viewmodels.RecordsFilter.Companion.noFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import model.Emotion
import model.MoodRecord

//@Stable
abstract class HistoryViewModel<T : HistoryRecord> : KMMViewModel() {
    abstract fun addRecord(moodRecord: MoodRecord)
    abstract val records: StateFlow<List<HistoryRecord>>

    val text: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue(""))

    internal val filter = MutableStateFlow(noFilter)
    internal val emotions = MutableStateFlow<List<Emotion>>(emptyList())

    fun onTextChanged(textFieldValue: TextFieldValue) {
        text.update {
            it.copy(
                annotatedString = textFieldValue.annotatedString,
                selection = textFieldValue.selection
            )
        }
        textToSearch(textFieldValue.text)
    }

    private fun textToSearch(text: String) {
        filter.update { it.copy(text = text) }
    }

    fun emotionClicked(emotion: Emotion) {
        filter.update {
            it.copy(
                emotions = if (it.emotions.contains(emotion)) {
                    it.emotions - emotion
                } else {
                    it.emotions + emotion
                }
            )
        }
    }
}
