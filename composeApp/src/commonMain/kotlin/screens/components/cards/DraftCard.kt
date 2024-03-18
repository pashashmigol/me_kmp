package screens.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.HistoryRecord
import screens.components.wheel.SmallWheel
import screens.components.wheel.WheelViewModel
import screens.history.viewmodels.HistoryViewModel
import screens.history.viewmodels.draft.DraftRecordViewModel
import screens.history.viewmodels.tags.TagsViewModel

@Composable
fun <T : HistoryRecord> DraftCard(
    draftViewModel: DraftRecordViewModel,
    historyViewModel: HistoryViewModel<T>,
    wheelViewModel: WheelViewModel,
    tagsViewModel: TagsViewModel,
) {
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
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    focusedIndicatorColor = Color.White,
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
                        label = it.nameCompact(),
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
//                        error()
                    }
                }
            }
        }
    }
}
