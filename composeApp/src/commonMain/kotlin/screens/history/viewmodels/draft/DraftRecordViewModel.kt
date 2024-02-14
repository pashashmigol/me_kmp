package com.me.screens.history.viewmodels.draft

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.me.model.Feeling
import com.me.model.MoodRecord
import kotlinx.coroutines.flow.MutableStateFlow

abstract class DraftRecordViewModel : ViewModel() {

    abstract var text: MutableStateFlow<TextFieldValue>

    abstract val feelings: List<Feeling>

    abstract val hasValidRecord: Boolean

    abstract val record: MoodRecord

    abstract fun clearRecord()

    abstract fun addFeeling(feeling: Feeling)

    abstract fun onTextChanged(newText: TextFieldValue)
}
