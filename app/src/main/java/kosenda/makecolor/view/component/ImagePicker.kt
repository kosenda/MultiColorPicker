package kosenda.makecolor.view.component

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.util.hexToColor
import kotlin.math.roundToInt

@Composable
fun ImagePicker(
    btm: Bitmap,
    horizontalPadding: Dp,
    updateColorData: (Color) -> Unit,
    heightToWidthRatio: Float = 1f,
) {
    val density = LocalDensity.current.density
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp - horizontalPadding * 2
    val screenHeight = screenWidth * heightToWidthRatio
    val widthRatio = (btm.width / density).dp / (screenWidth)
    val heightRatio = (btm.height / density).dp / screenHeight
    var posX by rememberSaveable { mutableStateOf(0f) }
    var posY by rememberSaveable { mutableStateOf(0f) }

    Box(
        modifier = Modifier.width(screenWidth).height(screenHeight),
    ) {
        Image(
            bitmap = btm.asImageBitmap(),
            contentDescription = "picture",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    awaitEachGesture {
                        val circleRadius = 10.dp.toPx()
                        val down = awaitFirstDown()
                        posX = down.position.x - circleRadius
                        posY = down.position.y - circleRadius
                        getPixelColor(
                            btm = btm,
                            x = (posX + circleRadius) * widthRatio,
                            y = (posY + circleRadius) * heightRatio,
                            updateColorData = updateColorData,
                        )

                        drag(down.id) { change ->
                            if (change.positionChange() != Offset.Zero) {
                                change.consume()
                            }
                            posX = change.position.x.coerceIn(
                                0f..btm.width / widthRatio,
                            ) - circleRadius
                            posY = change.position.y.coerceIn(
                                0f..btm.height / heightRatio,
                            ) - circleRadius
                            getPixelColor(
                                btm = btm,
                                x = (posX + circleRadius) * widthRatio,
                                y = (posY + circleRadius) * heightRatio,
                                updateColorData = updateColorData,
                            )
                        }
                    }
                },
        )
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        posX.roundToInt(),
                        posY.roundToInt(),
                    )
                }
                .size(48.dp),
            contentAlignment = Alignment.TopStart,
        ) {
            Canvas(
                modifier = Modifier.size(20.dp),
                onDraw = {
                    val radius = 6.dp
                    val circleStyle = Stroke(2.dp.toPx())
                    drawCircle(
                        color = Color.White,
                        radius = radius.toPx(),
                        style = circleStyle,
                    )
                    drawCircle(
                        color = Color.Gray,
                        radius = (radius - 2.dp).toPx(),
                        style = Stroke(1.dp.toPx()),
                    )
                },
            )
        }
    }
}

private fun getPixelColor(
    btm: Bitmap,
    x: Float,
    y: Float,
    updateColorData: (Color) -> Unit,
) {
    val xPx = x.coerceIn(0f..btm.width.toFloat() - 1f).toInt()
    val yPx = y.coerceIn(0f..btm.height.toFloat() - 1f).toInt()
    val btmPixel = btm.getPixel(xPx, yPx)
    updateColorData(hexToColor(hex = String.format("%06X", 0xFFFFFF and btmPixel)))
}
