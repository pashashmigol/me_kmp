package com.me.screens.components.wheel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.me.model.Fear

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SmallWheel(
    modifier: Modifier = Modifier,
    label: String? = null,
    textSize: TextUnit = 14.sp,
    colors: List<Color> = emptyList(),
    wheelViewModel: WheelViewModel? = null
) {
    var rect: Rect = remember { Rect.Zero }

    Box(modifier.padding(0.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .clip(RoundedCornerShape(50))
                .focusable(false)
                .onGloballyPositioned { coordinates ->
                    rect = Rect(
                        offset = coordinates.positionInRoot(),
                        size = coordinates.size.toSize()
                    )
                }
                .pointerInteropFilter { false }
                .clickable {
                    wheelViewModel?.bigWheelPosition?.value = rect
                }
                .drawWithContent {
                    colors.forEachIndexed { index, color ->
                        val sweepAngle = 360f / colors.size
                        sector(
                            drawScope = this,
                            color = color,
                            startAngle = index * sweepAngle - 90,
                            sweepAngle = sweepAngle,
                        )
                    }
                    val width = rect.minDimension / 50
                    drawCircle(
                        color = Color.White,
                        radius = rect.minDimension / 2 - width / 2,
                        style = Stroke(width = width)
                    )
                }
        )
        if (colors.size > 1) return
        label?.let {
            Text(
                text = it,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent)
                    .padding(4.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = textSize,
                softWrap = true,
                textAlign = TextAlign.Center,
                lineHeight = TextUnit(1f, TextUnitType.Em)
            )
        }
    }
}

@Preview
@Composable
fun SmallWheelPreview() {
    SmallWheel(
        modifier = Modifier
            .size(72.dp)
            .padding(8.dp),
        label = Fear.Excluded.nameCompact(LocalContext.current),
        colors = listOf(Fear.Excluded.color())
    )
}

fun sector(
    drawScope: DrawScope,
    color: Color,
    startAngle: Float,
    sweepAngle: Float
) {
    drawScope.drawArc(
        color = color,
        size = drawScope.size,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = true,
        blendMode = BlendMode.Src
    )
}
