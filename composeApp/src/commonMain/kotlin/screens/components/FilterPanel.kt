package screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.flow.update
import model.HistoryRecord
import screens.history.viewmodels.HistoryViewModel
import screens.history.viewmodels.tags.TagsViewModel
import com.me.resources.library.MR

@Composable
@Stable
fun <T : HistoryRecord> FilterPanel(
    historyViewModel: HistoryViewModel<T>,
    tagsViewModel: TagsViewModel,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = tagsViewModel.onTextChanged(
                        historyViewModel.text.collectAsState().value
                    ),
                    trailingIcon = {
                        Image(
                            painterResource(MR.images.ic_menu_search),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.wrapContentSize()
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .focusRequester(remember { FocusRequester() })
                        .testTag("text input"),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                    ),
                    onValueChange = { newText: TextFieldValue ->
                        historyViewModel.onTextChanged(newText)
                        tagsViewModel.onTextChanged(newText)
                    }
                )
                TextButton(
                    modifier = Modifier.testTag("hashtag button"),
                    onClick = {
                        historyViewModel.text.update {
                            tagsViewModel.onHashClicked(historyViewModel.text.value)
                        }
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
                        historyViewModel.text.update {
                            tagsViewModel.onMentionClicked(historyViewModel.text.value)
                        }
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
            ChooseEmotionPanel(historyViewModel)
        }
    }
}
