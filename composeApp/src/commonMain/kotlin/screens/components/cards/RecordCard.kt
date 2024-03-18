package screens.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screens.components.cards.utils.highlightSearch
import model.CompositeRecord
import model.Emotion
import model.HistoryRecord
import screens.components.wheel.SmallWheel
import screens.history.viewmodels.tags.TagsViewModel

private val corner = 16.dp
private val shape = AbsoluteRoundedCornerShape(corner, corner, corner, corner)

@Composable
fun RecordCard(
    record: HistoryRecord,
    tagsViewModel: TagsViewModel,
    contains: AnnotatedString = AnnotatedString(""),
    emotions: List<Emotion> = emptyList()
) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(shape)
            .background(Color.Black)
            .border(
                border = BorderStroke(1.dp, Color.White),
                shape = shape
            )
            .semantics { testTag = "record card" }
    ) {
        Column(Modifier.background(Color.Black)) {
            Row(
                Modifier
                    .background(Color.Black)
            ) {
                Column(
                    Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = record.dateString(),
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    Text(
                        text = highlightSearch(
                            text = record.text,
                            toSearch = contains.text
                        ).let { tagsViewModel.annotateString(it) },
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
                SmallWheel(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(72.dp),
                    label = record.feelings.firstOrNull()?.nameCompact(),
                    colors = record.colors,
                    textSize = 16.sp,
                )
            }
            if (contains.text.isNotBlank() || emotions.isNotEmpty()) {
                (record as? CompositeRecord)
                    ?.matchedRecords(contains.text, emotions)
                    ?.forEach {
                        Box(Modifier.padding(6.dp)) {
                            RecordCard(it, tagsViewModel, contains, emotions)
                        }
                    }
            }
        }
    }
}
