package kosenda.makecolor.model

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kosenda.makecolor.model.util.createShareBitmap
import java.io.File
import java.io.FileOutputStream
import java.util.*

fun shareIntent(
    hex1: String?,
    hex2: String?,
    btmHeight: Float,
    btmWidth: Float,
    density: Density,
    layoutDirection: LayoutDirection,
    context: Context,
    reviewURL: String,
) {
    val sendIntent: Intent = when {
        hex1 != null -> {
            val createdBitmap = createShareBitmap(
                hex1 = hex1,
                hex2 = hex2,
                btmHeight,
                btmWidth,
                density,
                layoutDirection,
            )
            val cachePath = File(context.cacheDir, "images").also { it.mkdirs() }
            val filePath = File(cachePath, "${Date().time}.png")
           FileOutputStream(filePath.absolutePath).apply {
                createdBitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
                close()
            }
            val imageUri = FileProvider.getUriForFile(
                context,
                "kosenda.makecolor.fileprovider",
                filePath,
            )
            val sendText = if (hex2 != null) {
                "#$hex1, #$hex2 \n$reviewURL"
            } else {
                "#$hex1 \n$reviewURL"
            }
            Intent().apply {
                action = Intent.ACTION_SEND
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setDataAndType(imageUri, "image/png")
                putExtra(Intent.EXTRA_TEXT, sendText)
                putExtra(Intent.EXTRA_STREAM, imageUri)
            }
        }
        else -> {
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, reviewURL)
                type = "text/plain"
            }
        }
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    ContextCompat.startActivity(context, shareIntent, null)
}
