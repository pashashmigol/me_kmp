package screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screens.components.cards.DraftCard
import screens.components.cards.RecordCard
import screens.components.wheel.Wheel
import screens.components.wheel.WheelViewModel
import screens.history.viewmodels.TodayRecordsViewModel
import screens.history.viewmodels.draft.DraftRecordViewModel
import screens.history.viewmodels.tags.TagsViewModel

@Composable
fun TodayScreen(
    historyViewModel: TodayRecordsViewModel,
    draftViewModel: DraftRecordViewModel,
    wheelViewModel: WheelViewModel,
    tagsViewModel: TagsViewModel,
) {
    val bigWheelPosition = wheelViewModel.bigWheelPosition
    val suggestions = tagsViewModel.suggestedTags.collectAsState()
    val todayRecords = historyViewModel.records.collectAsState()

    BoxWithConstraints(modifier = Modifier
        .fillMaxWidth()
        .semantics { testTag = "today screen root" }) {

        val density = LocalDensity.current.density
        bigWheelPosition.value?.let { position ->
            Wheel(
                modifier = Modifier.semantics { testTag = "big wheel" },
                smallWheelPosition = position,
                bigWheelSize = Size(
                    maxWidth.value * density,
                    maxHeight.value * density
                ),
                onFeeling = { feeling ->
                    bigWheelPosition.value = null
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

            items(suggestions.value.size) { index ->
                val reversedIndex = suggestions.value.lastIndex - index
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
                        text = suggestions.value[reversedIndex],
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent, CircleShape)
                            .padding(18.dp)
                    )
                }
            }

            items(todayRecords.value.size) { index ->
                val reversedIndex = todayRecords.value.lastIndex - index

                RecordCard(
                    record = todayRecords.value[reversedIndex],
                    tagsViewModel = tagsViewModel
                )
                Divider(
                    modifier = Modifier.height(12.dp), color = Color.Transparent
                )
            }
        }
    }
}
