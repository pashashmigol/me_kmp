package com.me.screens.components.cards

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.me.R
import com.me.data.Repository
import data.storage.StorageStub
import com.me.model.Feeling
import com.me.model.HistoryRecord
import com.me.model.MoodRecord
import com.me.screens.history.viewmodels.draft.DraftRecordViewModel
import com.me.screens.history.viewmodels.HistoryViewModel
import com.me.screens.history.viewmodels.HistoryViewModelStub
import com.me.screens.history.viewmodels.tags.TagsViewModel
import com.me.screens.history.viewmodels.tags.TagsViewModelReal
import data.utils.randomDate
import com.me.data.utils.randomString
import com.me.screens.components.wheel.SmallWheel
import com.me.screens.components.wheel.WheelViewModel
import com.me.screens.components.wheel.WheelViewModelStub
import kotlinx.coroutines.flow.MutableStateFlow
import screens.components.cards.CardButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : HistoryRecord> DraftCard(
    draftViewModel: DraftRecordViewModel,
    historyViewModel: HistoryViewModel<T>,
    wheelViewModel: WheelViewModel,
    tagsViewModel: TagsViewModel,
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("text input"),
                value = draftViewModel.text.collectAsState().value,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                onValueChange = { text: TextFieldValue ->
                    draftViewModel.onTextChanged(text)
                    tagsViewModel.onTextChanged(text)
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    modifier = Modifier.semantics { testTag = "hashtag button" },
                    onClick = {
                        draftViewModel.text.value =
                            tagsViewModel.onHashClicked(draftViewModel.text.value)
                    }
                ) {
                    Text(
                        text = "#",
                        color = Color.White,
                        fontSize = TextUnit(28.0f, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold,
                    )
                }
                TextButton(
                    modifier = Modifier.testTag("mention button"),
                    onClick = {
                        draftViewModel.text.value =
                            tagsViewModel.onMentionClicked(draftViewModel.text.value)
                    }
                ) {
                    Text(
                        text = "@",
                        color = Color.White,
                        fontSize = TextUnit(28.0f, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(8.dp)
        ) {
            if (draftViewModel.feelings.isEmpty()) {
                SmallWheel(
                    modifier = Modifier
                        .size(96.dp)
                        .padding(2.dp)
                        .semantics { testTag = "empty small wheel" },
                    wheelViewModel = wheelViewModel
                )
            } else {
                draftViewModel.feelings.forEach {
                    SmallWheel(
                        modifier = Modifier
                            .size(96.dp)
                            .padding(2.dp)
                            .semantics { testTag = "small wheel" },
                        label = it.nameCompact(context = context),
                        colors = listOf(it.color()),
                        wheelViewModel = wheelViewModel,
                        textSize = 20.sp
                    )
                }
            }
            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                CardButton(
                    modifier = Modifier.semantics { testTag = "cancel button" },
                    imageVector = Icons.Default.Close
                ) {
                    draftViewModel.clearRecord()
                }
                CardButton(
                    modifier = Modifier.semantics { testTag = "ok button" },
                    imageVector = Icons.Default.Check
                ) {
                    tagsViewModel.processNewTagsFromText(
                        draftViewModel.text.value
                    )

                    if (draftViewModel.hasValidRecord) {
                        historyViewModel.addRecord(draftViewModel.record)
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.select_emotion),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DraftCardPreview() {
    DraftCard(
        draftViewModel = DraftRecordViewModelStub(),
        historyViewModel = HistoryViewModelStub(),
        wheelViewModel = WheelViewModelStub(),
        tagsViewModel = TagsViewModelReal(Repository(StorageStub())),
    )
}

class DraftRecordViewModelStub : DraftRecordViewModel() {
    override var text = MutableStateFlow(TextFieldValue())
    override val record = MoodRecord(
        date = randomDate(),
        text = randomString(),
        feelings = listOf(Feeling.random())
    )

    override val feelings = mutableListOf(
        Feeling.random(),
    )

    override fun clearRecord() {}
    override fun addFeeling(feeling: Feeling) {}
    override fun onTextChanged(newText: TextFieldValue) {}
    override val hasValidRecord: Boolean = false
}
