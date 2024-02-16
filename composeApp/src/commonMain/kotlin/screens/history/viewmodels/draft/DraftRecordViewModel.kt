package screens.history.viewmodels.draft

import androidx.compose.ui.text.input.TextFieldValue
import com.rickclephas.kmm.viewmodel.KMMViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import model.Feeling
import model.MoodRecord

abstract class DraftRecordViewModel : KMMViewModel() {

    abstract var text: MutableStateFlow<TextFieldValue>

    abstract val feelings: List<Feeling>

    abstract val hasValidRecord: Boolean

    abstract val record: MoodRecord

    abstract fun clearRecord()

    abstract fun addFeeling(feeling: Feeling)

    abstract fun onTextChanged(newText: TextFieldValue)
}
