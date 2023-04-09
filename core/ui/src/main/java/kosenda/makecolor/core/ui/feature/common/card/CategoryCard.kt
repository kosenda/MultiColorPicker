package kosenda.makecolor.core.ui.feature.common.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun CategoryCard(category: String, size: Int, onClick: () -> Unit) {
    val cardHalfHeight = 28.dp
    val startShape = RoundedCornerShape(topStart = cardHalfHeight, bottomStart = cardHalfHeight)
    Box(
        modifier = Modifier
            .padding(start = 16.dp, bottom = 4.dp)
            .fillMaxWidth()
            .height(cardHalfHeight * 2)
            .clip(shape = startShape)
            .background(color = MaterialTheme.colorScheme.surface),
    ) {
        Card(
            modifier = Modifier
                .clip(shape = RectangleShape)
                .clip(shape = startShape)
                .clickable(onClick = onClick),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = category,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = size.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 16.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCategoryCard_Light() {
    MakeColorTheme(isDarkTheme = false) {
        CategoryCard(category = "temp", size = 1) {}
    }
}

@Preview
@Composable
private fun PreviewCategoryCard_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        CategoryCard(category = "temp", size = 1) {}
    }
}
