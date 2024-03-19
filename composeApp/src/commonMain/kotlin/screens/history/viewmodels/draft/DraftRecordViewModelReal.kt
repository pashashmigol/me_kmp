package screens.history.viewmodels.draft

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.input.TextFieldValue
import com.rickclephas.kmm.viewmodel.coroutineScope
import data.utils.now
import model.Feeling
import model.MoodRecord
import model.MoodRecord.Companion.MAX_EMOTIONS_NUMBER
import model.MoodRecord.Companion.MAX_TEXT_LENGTH
import model.MoodRecord.Companion.MIN_EMOTIONS_NUMBER
import model.MoodRecord.Companion.MIN_TEXT_LENGTH
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import screens.history.viewmodels.tags.TagsViewModel

class DraftRecordViewModelReal(
    private val tagsViewModel: TagsViewModel
) : DraftRecordViewModel() {

    override var text = MutableStateFlow(TextFieldValue())

    override val feelings = mutableStateListOf<Feeling>()

    override val hasValidRecord: Boolean
        get() {
            return text.value.text.length in MIN_TEXT_LENGTH..MAX_TEXT_LENGTH
                    && feelings.size in MIN_EMOTIONS_NUMBER..MAX_EMOTIONS_NUMBER
        }

    override fun clearRecord() {
        text.value = TextFieldValue()
        feelings.clear()
        viewModelScope.coroutineScope.launch {

            println("###: clearRecord(); suggestions emit: emptyList()")
            tagsViewModel.suggestedTags.emit(emptyList())
        }
    }

    override fun addFeeling(feeling: Feeling) {
        if (feelings.size == MAX_EMOTIONS_NUMBER) {
            feelings.clear()
        }
        feelings.add(feeling)
    }

    override fun onTextChanged(newText: TextFieldValue) {
        text.value = tagsViewModel.onTextChanged(newText)
    }

    override val record: MoodRecord
        get() {
            if (!hasValidRecord) {
                throw IllegalStateException(
                    "can't create Record, check hasValidRecord field before"
                )
            }
            val record = MoodRecord(
                date = now(),
                text = text.value.text,
                feelings = feelings.toList()
            )
            clearRecord()
            return record
        }
}
