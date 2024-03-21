package screens.history.viewmodels.tags

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import com.rickclephas.kmm.viewmodel.KMMViewModel
import kotlinx.coroutines.flow.StateFlow

@Stable
abstract class TagsViewModel : KMMViewModel() {
    abstract fun onTextChanged(newText: TextFieldValue): TextFieldValue

    abstract fun onSuggestionClicked(index: Int, currentText: TextFieldValue): TextFieldValue

    abstract fun onHashClicked(current: TextFieldValue): TextFieldValue

    abstract fun onMentionClicked(current: TextFieldValue): TextFieldValue

    abstract fun processNewTagsFromText(textFieldValue: TextFieldValue)

    abstract fun annotateString(text: AnnotatedString): AnnotatedString

    abstract val suggestedTags: StateFlow<List<String>>
}
