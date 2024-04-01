package screens.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.onEach
import model.CompositeRecord
import kotlinx.coroutines.flow.update
import model.HistoryRecord
import screens.components.FilterPanel
import screens.components.cards.RecordCard
import screens.history.viewmodels.HistoryViewModel
import screens.history.viewmodels.tags.TagsViewModel

private val corner = 16.dp
private val shape = AbsoluteRoundedCornerShape(corner, corner, corner, corner)

@Composable
fun <T : HistoryRecord> HistoryScreen(
    historyViewModel: HistoryViewModel<T>,
    tagsViewModel: TagsViewModel,
    onItemClick: ((index: Int) -> Unit)? = null,
) {
    val context = rememberCompositionContext().effectCoroutineContext
    val suggestions: State<List<String>> = tagsViewModel.suggestedTags
        .collectAsState(emptyList(), context)

    val records: State<List<HistoryRecord>> = historyViewModel.records
        .onEach { println("### HistoryScreen.onEach: $it") }
        .collectAsState(emptyList(), context)

    println("### HistoryScreen: records.size = ${records.value.size}")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.Bottom,
        reverseLayout = true
    ) {
        item(key = "00") {
            Spacer(modifier = Modifier.height(150.dp))
        }
        item(key = "01") {
            Spacer(modifier = Modifier.height(150.dp))
        }
        item(key = "1") {
            Box(modifier = Modifier.height(150.dp)) {
                FilterPanel(
                    historyViewModel = historyViewModel,
                    tagsViewModel = tagsViewModel,
                )
            }
        }
        items(
            count = suggestions.value.size,
            key = { "suggestion_$it" }
        ) { index ->
            val reversedIndex = suggestions.value.lastIndex - index
            TextButton(
                modifier = Modifier
                    .background(Color.Transparent)
                    .testTag("suggestion"),
                onClick = {
                    historyViewModel.text.update {
                        tagsViewModel.onSuggestionClicked(
                            index = reversedIndex,
                            currentText = historyViewModel.text.value
                        )
                    }
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

        items(
            count = records.value.size,
            key = { "record_$it" }
        ) { index ->
            val reversedIndex = records.value.lastIndex - index
            Box(
                modifier = Modifier
                    .clickable {
                        (records.value[reversedIndex] as? CompositeRecord)?.let {
                            onItemClick?.invoke(it.index)
                        }
                    }
                    .fillParentMaxWidth()
                    .height(100.dp)
                    .clip(shape)
                    .background(Color.Black)
                    .border(
                        border = BorderStroke(1.dp, Color.White),
                        shape = shape
                    )
            ) {
                RecordCard(
                    record = records.value[reversedIndex],
                    tagsViewModel = tagsViewModel,
                    contains = historyViewModel.text.collectAsState().value.annotatedString,
                    emotions = historyViewModel.filter.collectAsState().value.emotions
                )
            }
            Divider(
                modifier = Modifier.height(12.dp),
                color = Color.Transparent
            )
        }
    }
}
