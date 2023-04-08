package kosenda.makecolor.view.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kosenda.makecolor.R
import kosenda.makecolor.core.model.data.ColorItem
import kosenda.makecolor.core.util.blackOrWhiteFromRGB
import kosenda.makecolor.core.util.hexToColor
import kosenda.makecolor.core.util.hexToRGB

@Composable
fun ColorCard(
    colorItem: ColorItem,
    onClick: () -> Unit,
    onDelete: ((ColorItem) -> Unit)? = null,
) {
    val foregroundColor = blackOrWhiteFromRGB(hexToRGB(hex = colorItem.hex))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .padding(bottom = 4.dp)
            .padding(horizontal = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = hexToColor(hex = colorItem.hex),
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "#${colorItem.hex}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = foregroundColor,
                )
                Text(
                    text = colorItem.memo,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = foregroundColor,
                )
            }
            if (onDelete != null) {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable(onClick = { onDelete(colorItem) }),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_close_24),
                        contentDescription = "delete color",
                        colorFilter = ColorFilter.tint(color = foregroundColor),
                    )
                }
            }
        }
    }
}
