package com.me.screens.history.viewmodels.tags

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class TagsViewModel : ViewModel() {
    abstract fun onTextChanged(newText: TextFieldValue): TextFieldValue

    abstract fun onSuggestionClicked(index: Int, currentText: TextFieldValue): TextFieldValue

    abstract fun onHashClicked(current: TextFieldValue): TextFieldValue

    abstract fun onMentionClicked(current: TextFieldValue): TextFieldValue

    abstract fun processNewTagsFromText(textFieldValue: TextFieldValue)

    abstract fun annotateString(text: AnnotatedString): AnnotatedString

    abstract val suggestedTags: MutableStateFlow<List<String>>
}
