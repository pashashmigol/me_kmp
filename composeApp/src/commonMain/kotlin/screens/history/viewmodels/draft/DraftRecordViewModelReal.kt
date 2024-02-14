package com.me.screens.history.viewmodels.draft

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.input.TextFieldValue
import com.me.model.Feeling
import com.me.model.MoodRecord
import com.me.model.MoodRecord.Companion.MAX_EMOTIONS_NUMBER
import com.me.model.MoodRecord.Companion.MAX_TEXT_LENGTH
import com.me.model.MoodRecord.Companion.MIN_EMOTIONS_NUMBER
import com.me.model.MoodRecord.Companion.MIN_TEXT_LENGTH
import com.me.screens.history.viewmodels.tags.TagsViewModel
import korlibs.time.DateTime
import kotlinx.coroutines.flow.MutableStateFlow

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
        tagsViewModel.suggestedTags.value = emptyList()
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
                date = DateTime.nowLocal(),
                text = text.value.text,
                feelings = feelings.toList()
            )
            clearRecord()
            return record
        }
}
