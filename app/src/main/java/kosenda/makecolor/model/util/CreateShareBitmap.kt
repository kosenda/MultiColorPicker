package kosenda.makecolor.model.util

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

fun createShareBitmap(
    hex1: String,
    hex2: String?,
    btmHeight: Float,
    btmWidth: Float,
    density: Density,
    layoutDirection: LayoutDirection,
): Bitmap {
    val imageBitmap = ImageBitmap(btmHeight.toInt(), (btmWidth.toInt()))
    val size = Size(btmHeight, btmWidth)
    CanvasDrawScope().draw(density, layoutDirection, Canvas(imageBitmap), size) {
        if (hex2 != null) {
            drawRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        hexToColor(hex1),
                        hexToColor(hex2),
                    ),
                ),
                size = size,
            )
        } else {
            drawRect(
                color = hexToColor(hex1),
                size = size,
            )
        }
    }
    return imageBitmap.asAndroidBitmap()
}
