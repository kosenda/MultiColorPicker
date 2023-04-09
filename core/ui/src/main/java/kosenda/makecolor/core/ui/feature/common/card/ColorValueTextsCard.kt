package kosenda.makecolor.core.ui.feature.common.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.model.data.ColorData
import kosenda.makecolor.core.ui.R
import kosenda.makecolor.core.ui.feature.common.ContentDivider
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.util.hexToHexStrWithSharp
import kosenda.makecolor.core.util.randomColorData

@Composable
fun ColorValueTextsCard(
    modifier: Modifier = Modifier,
    colorData: ColorData,
    withCopyImage: Boolean = true,
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Card(
        modifier = modifier.padding(top = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
        ) {
            ColorValueText(
                title = "RGB",
                text = colorData.rgb.toString(),
                clipboardManager = clipboardManager,
                withCopyImage = withCopyImage,
            )
            ContentDivider()
            ColorValueText(
                title = "CMYK",
                text = colorData.cmyk.toString(),
                clipboardManager = clipboardManager,
                withCopyImage = withCopyImage,
            )
            ContentDivider()
            ColorValueText(
                title = "HSV",
                text = colorData.hsv.toString(),
                clipboardManager = clipboardManager,
                withCopyImage = withCopyImage,
            )
            ContentDivider()
            ColorValueText(
                title = "HEX",
                text = hexToHexStrWithSharp(colorData.hex),
                clipboardManager = clipboardManager,
                withCopyImage = withCopyImage,
            )
        }
    }
}

@Composable
private fun ColorValueText(
    title: String,
    text: String,
    clipboardManager: ClipboardManager,
    withCopyImage: Boolean = true,
) {
    Row(
        modifier = if (withCopyImage) {
            Modifier
                .clip(shape = RoundedCornerShape(size = 8.dp))
                .clickable {
                    clipboardManager.setText(AnnotatedString(text))
                }
        } else {
            Modifier.clip(shape = RoundedCornerShape(size = 8.dp))
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = title,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                text = text,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (withCopyImage) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_content_copy_24),
                contentDescription = stringResource(id = R.string.copy),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewColorValueTextsCard_Light() {
    MakeColorTheme(isDarkTheme = false) {
        ColorValueTextsCard(colorData = randomColorData())
    }
}

@Preview
@Composable
private fun PreviewColorValueTextsCard_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        ColorValueTextsCard(colorData = randomColorData())
    }
}
