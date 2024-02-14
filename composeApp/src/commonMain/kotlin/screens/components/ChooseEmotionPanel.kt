package screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Emotion
import com.me.model.HistoryRecord
import com.me.model.color
import com.me.screens.history.viewmodels.HistoryViewModel

@Composable
fun <T : HistoryRecord> ChooseEmotionPanel(
    historyViewModel: HistoryViewModel<T>,
) {
    Row(Modifier.horizontalScroll(rememberScrollState())) {
        Emotion.values.forEach {
            EmotionToggle(it, historyViewModel)
        }
    }
}

@Composable
fun <T : HistoryRecord> EmotionToggle(
    emotion: Emotion,
    historyViewModel: HistoryViewModel<T>
) {
    val selected = historyViewModel.filter.collectAsState().value.isSelected(emotion)
    Box(
        modifier = Modifier
            .size(72.dp)
            .padding(8.dp)
            .alpha(if (selected) 1.0f else 0.5f),
    ) {
        OutlinedButton(
            modifier = Modifier.fillMaxSize(),
            onClick = { historyViewModel.emotionClicked(emotion) },
            border = BorderStroke(2.dp, Color.White),
            shape = CircleShape,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = emotion.color()
            ),
        ) {
        }
        Text(
            text = emotion.nameCompact(),
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .wrapContentHeight(align = Alignment.CenterVertically),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            softWrap = true,
            textAlign = TextAlign.Center,
            lineHeight = TextUnit(1f, TextUnitType.Em)
        )
    }
}

//@Preview
//@Composable
//fun ChooseEmotionPanelPreview() = ChooseEmotionPanel(HistoryViewModelStub())
