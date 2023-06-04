package kosenda.makecolor.core.ui.feature.common.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.feature.theme.contentBrush
import kosenda.makecolor.core.ui.feature.theme.primaryBrush

@Composable
fun TitleCard(
    modifier: Modifier = Modifier,
    topPadding: Dp = 24.dp,
    text: String,
    painter: Painter,
) {
    Card(
        modifier = modifier.padding(top = topPadding, bottom = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .defaultMinSize(minHeight = 48.dp)
                .contentBrush(brush = primaryBrush()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painter,
                contentDescription = text,
                modifier = Modifier.size(28.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiaryContainer),
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp),
                color = MaterialTheme.colorScheme.onTertiaryContainer,
            )
        }
    }
}
