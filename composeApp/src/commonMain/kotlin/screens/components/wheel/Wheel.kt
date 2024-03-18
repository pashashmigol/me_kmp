package screens.components.wheel

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import model.Emotion
import model.Feeling
import model.color
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

val textStyle = TextStyle(
    brush = SolidColor(Color.White),
    fontStyle = FontStyle.Italic,
    fontSize = TextUnit(24f, type = TextUnitType.Sp),
)

@Composable
fun Wheel(
    modifier: Modifier,
    smallWheelPosition: Rect,
    bigWheelSize: Size,
    onEmotion: (Emotion?) -> Unit = {},
    onFeeling: (Feeling?) -> Unit = {}
) {
    val rotation: Animatable<Float, AnimationVector1D> = remember { Animatable(0f) }
    val velocity: MutableState<Float> = remember { mutableFloatStateOf(0f) }

    val minRadius = remember { smallWheelPosition.size.minDimension / 2 }
    val measuredWidth = remember { bigWheelSize.width }
    val measuredHeight = remember { bigWheelSize.height }

    val maxRadius = measuredWidth / 2

    val maxSize = Size(measuredWidth, measuredWidth)
    if (maxSize == Size.Unspecified) {
        return
    }

    val radius: Animatable<Float, AnimationVector1D> = remember { Animatable(minRadius) }
    val items = Emotion.values()

    val scope = rememberCoroutineScope()
    val textMeasurer = rememberTextMeasurer()
    val chosenEmotion = remember<MutableState<Emotion?>> { mutableStateOf(null) }

    val center = Offset(
        x = maxRadius,
        y = maxRadius
    )

    val offset = smallWheelPosition.center - Offset(center.x, center.y)
    if (offset == Offset.Unspecified) {
        return
    }

    fun handleDrag(change: PointerInputChange) {
        val start = change.previousPosition - offset
        val end = change.position - offset

        val endDeg = toDegrees(atan2(end.y - center.y, end.x - center.x).toDouble())
        val startDeg = toDegrees(atan2(start.y - center.y, start.x - center.x).toDouble())

        val degrees = anglesDifference(startDeg, endDeg).toFloat()
        velocity.value = 200 * degrees / (change.uptimeMillis - change.previousUptimeMillis)

        println("radEnd = $endDeg; radStart = $startDeg; rad = $degrees; velocity = ${velocity.value}")
        scope.launch {
            rotation.snapTo(rotation.value + degrees)
        }
    }

    Spacer(modifier
        .size(
            width = measuredHeight.dp,
            height = measuredHeight.dp
        )
        .background(Color.Transparent)
        .zIndex(1f)
        .pointerInput("drag") {
            detectDragGestures(
                onDragEnd = {
                    scope.launch {
                        rotation.animateDecay(
                            initialVelocity = velocity.value,
                            animationSpec = exponentialDecay(
                                absVelocityThreshold = 0.5f
                            ),
                        )
                    }
                },
                onDrag = { change, _ ->
                    handleDrag(change)
                })
        }
        .pointerInput("clicks") {
            detectTapGestures(
                onPress = { it: Offset ->
                    scope.launch {
                        if (clickRadius(it, center + offset) > maxRadius) {
                            onFeeling(null)
                            onEmotion(null)
                        } else {
                            if (radius.value < maxRadius) {
                                radius.animateTo(maxRadius)
                            } else {
                                radius.animateTo(minRadius)
                            }
                        }
                    }
                },
                onTap = {
                    val valid = clickRadius(it, center + offset) < maxRadius

                    if (!valid) {
                        return@detectTapGestures
                    }
                    scope.launch {
                        radius.animateTo(maxRadius)
                    }
                    if (chosenEmotion.value == null) {
                        val emotion = Emotion.values[detectSectorIndex(
                            click = it,
                            center = center + offset,
                            sectorAngle = 360f / Emotion.values.size,
                            rotation = rotation
                        )]
                        onEmotion(emotion)
                        chosenEmotion.value = emotion

                    } else {
                        val feeling = chosenEmotion.value?.feelings?.get(
                            detectSectorIndex(
                                click = it,
                                center = center + offset,
                                sectorAngle = 360f / chosenEmotion.value!!.feelings.size,
                                rotation = rotation
                            )
                        )
                        chosenEmotion.value = null
                        onFeeling(feeling)
                    }
                })
        }
        .drawWithContent {
            chosenEmotion.value?.let {
                secondWheel(
                    emotion = it,
                    rotation = rotation,
                    offset = offset,
                    maxSize = maxSize,
                    radius = maxRadius,
                    textMeasurer = textMeasurer
                )
                return@drawWithContent
            }
            firstWheel(
                items = items,
                rotation = rotation,
                offset = offset,
                maxSize = maxSize,
                radius = maxRadius,
                textMeasurer = textMeasurer
            )
        })
}

private fun anglesDifference(angle1: Double, angle2: Double): Double {
    val diff = (angle2 - angle1 + 180) % 360 - 180
    return if (diff < -180) diff + 360 else diff
}

private fun ContentDrawScope.secondWheel(
    emotion: Emotion,
    rotation: Animatable<Float, AnimationVector1D>,
    offset: Offset,
    maxSize: Size,
    radius: Float,
    textMeasurer: TextMeasurer
) {
    val sectorAngle = 360f / emotion.feelings.size
    emotion.feelings.forEachIndexed { index, feeling ->
        sector(
            drawScope = this,
            color = emotion.color(),
            startAngle = index * sectorAngle + rotation.value,
            sweepAngle = sectorAngle,
            offset = offset,
            maxSize = maxSize,
        )
        text(
            drawScope = this,
            text = feeling.nameTranslated(),
            offset = offset,
            angle = index * sectorAngle + sectorAngle / 2 + rotation.value + 180,
            textMeasurer = textMeasurer,
            maxSize = maxSize,
        )
    }
    emotion.feelings.forEachIndexed { index, _ ->
        sectorDivider(
            drawScope = this,
            startAngle = index * sectorAngle + rotation.value,
            offset = offset,
            radius = radius,
            maxSize = maxSize
        )
    }
}

private fun ContentDrawScope.firstWheel(
    items: List<Emotion>,
    rotation: Animatable<Float, AnimationVector1D>,
    offset: Offset,
    maxSize: Size,
    radius: Float,
    textMeasurer: TextMeasurer
) {
    val sectorAngle = 360f / Emotion.values.size

    items.forEachIndexed { index, emotion: Emotion? ->
        sector(
            drawScope = this,
            color = emotion?.color() ?: Color.Black,
            startAngle = index * sectorAngle + rotation.value,
            sweepAngle = sectorAngle,
            offset = offset,
            maxSize = maxSize,
        )
        text(
            drawScope = this,
            text = emotion?.name() ?: "$emotion",
            offset = offset,
            angle = index * sectorAngle + rotation.value + 180 + sectorAngle / 2,
            textMeasurer = textMeasurer,
            maxSize = maxSize,
        )
    }
    items.forEachIndexed { index, _ ->
        sectorDivider(
            drawScope = this,
            startAngle = index * sectorAngle + rotation.value,
            offset = offset,
            radius = radius,
            maxSize = maxSize
        )
    }
}

private fun detectSectorIndex(
    click: Offset,
    center: Offset,
    sectorAngle: Float,
    rotation: Animatable<Float, AnimationVector1D>
): Int {
    val clickDegrees = toDegrees(
        (atan2(click.x - center.x, click.y - center.y)).toDouble()
    )
    val degrees = 360 - ((clickDegrees + rotation.value) % 360 + 360) % 360
    val amount = 360 / sectorAngle.toInt()
    return ((degrees + 90) / sectorAngle).toInt() % amount
}

private fun clickRadius(
    point: Offset, center: Offset
): Float {
    val dx = (point.x - center.x).toDouble()
    val dy = (point.y - center.y).toDouble()
    return sqrt(dx * dx + dy * dy).toFloat()
}

fun sector(
    drawScope: DrawScope,
    color: Color,
    offset: Offset,
    startAngle: Float,
    sweepAngle: Float,
    maxSize: Size,
) {
    sectorPart(drawScope, 1f, color, startAngle, sweepAngle, offset, maxSize)
    sectorPart(drawScope, .7f, color, startAngle, sweepAngle, offset, maxSize)
    sectorPart(drawScope, .4f, color, startAngle, sweepAngle, offset, maxSize)
}

fun text(
    drawScope: DrawScope,
    text: String,
    offset: Offset,
    angle: Float,
    textMeasurer: TextMeasurer,
    maxSize: Size,
) {
    val textSize = Size(width = 350f, height = 100f)

    val pointOriginal =
        offset + Offset(maxSize.maxDimension / 2, maxSize.maxDimension / 2)
    val topLeft = pointOriginal + Offset(-textSize.width - 150, -textSize.height / 2)

    drawScope.translate(-0f, -0f) {
        rotate(degrees = angle, pivot = pointOriginal) {
            drawText(
                textMeasurer = textMeasurer,
                text = text,
                style = textStyle,
                topLeft = topLeft,
                softWrap = false,
                size = textSize,
                maxLines = 1,
                overflow = Ellipsis
            )
        }
    }
}

private fun sectorPart(
    drawScope: DrawScope,
    scale: Float,
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    offset: Offset,
    maxSize: Size,
) {
    val size = maxSize * scale
    val center = offset + Offset(maxSize.maxDimension / 2, maxSize.maxDimension / 2)
    val correctedOffset = center - Offset(size.width / 2, size.height / 2)

    drawScope.drawArc(
        color = color,
        alpha = 1.4f - scale,
        size = size,
        topLeft = correctedOffset,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = true,
        blendMode = BlendMode.SrcOver
    )
}

private fun sectorDivider(
    drawScope: DrawScope,
    startAngle: Float,
    offset: Offset,
    radius: Float,
    maxSize: Size,
) {
    val center = offset + Offset(maxSize.maxDimension / 2, maxSize.maxDimension / 2)
    val end = center + Offset(
        x = radius * cos(toRadians(startAngle.toDouble())).toFloat(),
        y = radius * sin(toRadians(startAngle.toDouble())).toFloat(),
    )

    drawScope.drawLine(
        color = Color.Black,
        start = center,
        end = end,
    )
}

