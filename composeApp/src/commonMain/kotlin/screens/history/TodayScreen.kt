package com.me.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.me.data.Repository
import data.storage.StorageStub
import com.me.model.HistoryRecord
import com.me.screens.history.viewmodels.draft.DraftRecordViewModel
import com.me.screens.history.viewmodels.tags.TagsViewModel
import com.me.screens.history.viewmodels.tags.TagsViewModelReal
import com.me.screens.history.viewmodels.TodayRecordsViewModel
import com.me.screens.components.cards.DraftCard
import com.me.screens.components.cards.DraftRecordViewModelStub
import com.me.screens.components.cards.RecordCard
import com.me.screens.components.wheel.Wheel
import com.me.screens.components.wheel.WheelViewModel
import com.me.screens.components.wheel.WheelViewModelStub

@Composable
fun TodayScreen(
    historyViewModel: TodayRecordsViewModel,
    draftViewModel: DraftRecordViewModel,
    wheelViewModel: WheelViewModel,
    tagsViewModel: TagsViewModel,
) {
    var bigWheelPosition by remember { wheelViewModel.bigWheelPosition }

    val suggestions: List<String> by tagsViewModel.suggestedTags.collectAsStateWithLifecycle()
    val todayRecords: List<HistoryRecord>
            by historyViewModel.records.collectAsStateWithLifecycle(emptyList())

    Surface(modifier = Modifier
        .fillMaxWidth()
        .semantics { testTag = "today screen root" }) {
        bigWheelPosition?.let { position ->
            Wheel(
                modifier = Modifier.semantics { testTag = "big wheel" },
                smallWheelPosition = position,
                onFeeling = { feeling ->
                    bigWheelPosition = null
                    feeling?.let { draftViewModel.addFeeling(it) }
                })
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(2.dp),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.Bottom,
            reverseLayout = true
        ) {
            item { Spacer(modifier = Modifier.height(300.dp)) }

            item {
                DraftCard(
                    draftViewModel = draftViewModel,
                    historyViewModel = historyViewModel,
                    wheelViewModel = wheelViewModel,
                    tagsViewModel = tagsViewModel,
                )
            }

            items(suggestions.size) { index ->
                val reversedIndex = suggestions.lastIndex - index
                TextButton(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .testTag("suggestion"),
                    onClick = {
                        draftViewModel.text.value = tagsViewModel.onSuggestionClicked(
                            index = reversedIndex,
                            currentText = draftViewModel.text.value
                        )
                    },
                ) {
                    Text(
                        text = suggestions[reversedIndex],
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent, CircleShape)
                            .padding(18.dp)
                    )
                }
            }

            items(todayRecords.size) { index ->
                val reversedIndex = todayRecords.lastIndex - index

                RecordCard(
                    record = todayRecords[reversedIndex],
                    tagsViewModel = tagsViewModel
                )
                Divider(
                    modifier = Modifier.height(12.dp), color = Color.Transparent
                )
            }
        }
    }
}

@Preview
@Composable
private fun TodayScreenPreview() {
    TodayScreen(
        historyViewModel = TodayRecordsViewModel(Repository(StorageStub())),
        draftViewModel = DraftRecordViewModelStub(),
        wheelViewModel = WheelViewModelStub(),
        tagsViewModel = TagsViewModelReal(Repository(StorageStub())),
    )
}
