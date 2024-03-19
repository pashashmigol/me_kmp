package screens.history.viewmodels.tags

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import com.rickclephas.kmm.viewmodel.coroutineScope
import data.Repository
import data.utils.now
import model.HashTag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.Mention

class TagsViewModelReal(
    val repo: Repository
) : TagsViewModel() {

    override fun onTextChanged(newText: TextFieldValue): TextFieldValue {
        return newText.copy(
            annotatedString = annotateString(newText.annotatedString),
        ).also {
            viewModelScope.coroutineScope.launch {
                updateSuggestions(newText.text)
            }
        }
    }

    override fun onHashClicked(current: TextFieldValue): TextFieldValue {
        return onTextChanged(
            current.copy(
                text = current.text + "#",
                selection = TextRange(current.text.length + 1),
            )
        )
    }

    override fun onMentionClicked(current: TextFieldValue): TextFieldValue {
        return onTextChanged(
            current.copy(
                text = current.text + "@",
                selection = TextRange(current.text.length + 1),
            )
        )
    }

    override fun processNewTagsFromText(textFieldValue: TextFieldValue) {
        val regex = Regex(TAG_PATTERN)
        val text = textFieldValue.text

        var mr = regex.find(text)

        while (mr != null) {
            if (mr.value.startsWith("@")) {
                val mention = Mention(
                    value = mr.value,
                    lastUsed = now()
                )
                repo.addMention(mention)
            } else if (mr.value.startsWith("#")) {
                val mention = HashTag(
                    value = mr.value,
                    lastUsed = now()
                )
                repo.addTag(mention)
            }
            mr = mr.next()
        }
    }

    override fun onSuggestionClicked(
        index: Int,
        currentText: TextFieldValue
    ): TextFieldValue = currentText.copy(
        annotatedString = annotateString(
            AnnotatedString(
                completeTag(
                    current = currentText.text,
                    tag = suggestedTags.value.getOrNull(index) ?: "",
                )
            )
        ),
        selection = TextRange(index = Int.MAX_VALUE)
    ).also {
        viewModelScope.coroutineScope.launch {
            println("###: onSuggestionClicked(); suggestions emit: emptyList()")
            suggestedTags.emit(emptyList())
        }
    }

    override fun annotateString(text: AnnotatedString): AnnotatedString = buildAnnotatedString {
        append(text)

        Regex(TAG_PATTERN).findAll(text).forEach { matchResult ->
            addStyle(
                style = SpanStyle(color = Color(238, 181, 68)),
                start = matchResult.range.first,
                end = matchResult.range.last + 1,
            )
        }
    }

    private suspend fun updateSuggestions(string: String) {
        if (string.isBlank()) {
            println("###: suggestions emit: emptyList()")
            suggestedTags.emit(emptyList())
            return
        }
        Regex(LAST_TAG_PATTERN).find(string)?.value?.let { found ->
            (if (found.startsWith("@")) {
                repo.mentions.value
                    .map { it.value }
                    .sortedBy { it.lastUsed }
                    .map { it.value }
            } else if (found.startsWith("#")) {
                repo.tags.value
                    .map { it.value }
                    .sortedBy { it.lastUsed }
                    .map { it.value }
            } else {
                emptyList()
            }).filter {
                kotlin.runCatching { it.contains(found) }.getOrDefault(false)
            }.let { suggestions ->
                println("###: suggestions emit:$suggestions")
                suggestedTags.emit(suggestions)
            }
        }
    }

    override val suggestedTags = MutableStateFlow<List<String>>(emptyList())

    companion object {
        internal const val TAG_PATTERN = "(?<!\\w)[@#][\\w\']*"
        internal const val LAST_TAG_PATTERN = "(?<!\\w)[@#][\\w\']*\$"
    }
}
