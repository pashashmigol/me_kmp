package com.me.screens.history

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.me.model.HistoryRecord
import model.CompositeRecord
import com.me.screens.history.viewmodels.HistoryViewModel
import com.me.screens.history.viewmodels.tags.TagsViewModel
import com.me.screens.components.FilterPanel
import com.me.screens.components.cards.RecordCard
import kotlinx.coroutines.flow.update

@Composable
fun <T : HistoryRecord> HistoryScreen(
    historyViewModel: HistoryViewModel<T>,
    tagsViewModel: TagsViewModel,
    onItemClick: ((index: Int) -> Unit)? = null,
) {
    val suggestions: List<String> by tagsViewModel.suggestedTags.collectAsStateWithLifecycle()
    val records: List<HistoryRecord> by historyViewModel.records.collectAsStateWithLifecycle(
        emptyList()
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.Bottom,
        reverseLayout = true
    ) {
        item { Spacer(modifier = Modifier.height(300.dp)) }

        item {
            FilterPanel(
                historyViewModel = historyViewModel,
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
                    historyViewModel.text.update {
                        tagsViewModel.onSuggestionClicked(
                            index = reversedIndex,
                            currentText = historyViewModel.text.value
                        )
                    }
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

        items(records.size) { index ->
            val reversedIndex = records.lastIndex - index
            Box(
                modifier = Modifier
                    .clickable {
                        (records[reversedIndex] as? CompositeRecord)?.let {
                            onItemClick?.invoke(it.index)
                        }
                    }
                    .testTag("")
            ) {
                RecordCard(
                    record = records[reversedIndex],
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
