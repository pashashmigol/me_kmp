package com.me.screens.history.viewmodels

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.me.model.Emotion
import com.me.model.HistoryRecord
import com.me.model.MoodRecord
import com.me.screens.history.viewmodels.Filter.Companion.noFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

abstract class HistoryViewModel<T : HistoryRecord> : ViewModel() {
    abstract fun addRecord(moodRecord: MoodRecord)
    abstract val records: Flow<List<HistoryRecord>>

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

data class Filter(val text: String, val emotions: List<Emotion>) {
    fun isSelected(emotion: Emotion) = emotions.contains(emotion)

    companion object {
        val noFilter = Filter(text = "", emotions = emptyList())
    }
}
