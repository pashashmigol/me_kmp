package com.me.screens.history.viewmodels.tags

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.me.data.Repository
import model.HashTag
import com.me.model.Mention
import dagger.hilt.android.lifecycle.HiltViewModel
import korlibs.time.DateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModelReal @Inject constructor(
    val repository: Repository
) : TagsViewModel() {

    override fun onTextChanged(newText: TextFieldValue): TextFieldValue {
        return newText.copy(
            annotatedString = annotateString(newText.annotatedString),
        ).also {
            updateSuggestions(newText.text)
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
                    lastUsed = DateTime.now()
                )
                repository.addMention(mention)
            } else if (mr.value.startsWith("#")) {
                val mention = HashTag(
                    value = mr.value,
                    lastUsed = DateTime.now()
                )
                repository.addTag(mention)
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
        viewModelScope.launch { suggestedTags.emit(emptyList()) }
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

    private fun updateSuggestions(string: String) {
        if (string.isBlank()) {
            suggestedTags.update { emptyList() }
            return
        }
        Regex(LAST_TAG_PATTERN).find(string)?.value?.let { found ->
            (if (found.startsWith("@")) {
                repository.mentions.value
                    .map { it.value }
                    .sortedBy { it.lastUsed }
                    .map { it.value }
            } else if (found.startsWith("#")) {
                repository.tags.value
                    .map { it.value }
                    .sortedBy { it.lastUsed }
                    .map { it.value }
            } else {
                emptyList()
            }).filter {
                kotlin.runCatching { it.contains(found) }.getOrDefault(false)
            }.let { suggestions ->
                suggestedTags.update { suggestions }
            }
        }
    }

    override val suggestedTags = MutableStateFlow<List<String>>(emptyList())

    companion object {
        internal const val TAG_PATTERN = "(?<!\\w)[@#][\\w\']*"
        internal const val LAST_TAG_PATTERN = "(?<!\\w)[@#][\\w\']*\$"
    }
}
