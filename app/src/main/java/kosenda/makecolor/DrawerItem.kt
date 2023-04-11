package kosenda.makecolor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.feature.common.LocalIsDark
import kosenda.makecolor.core.ui.feature.theme.changeBrightness
import kosenda.makecolor.core.ui.feature.theme.contentBrush
import kosenda.makecolor.core.ui.feature.theme.primaryBrush

@Composable
fun DrawerItem(
    item: NavigationItems,
    isSelected: Boolean,
    onItemClick: (NavigationItems) -> Unit,
) {
    val displayNavigationItemTitles = when (item) {
        NavigationItems.Picker -> stringResource(id = R.string.picker_title)
        NavigationItems.Seekbar -> stringResource(id = R.string.seekbar_title)
        NavigationItems.Text -> stringResource(id = R.string.text_title)
        NavigationItems.Picture -> stringResource(id = R.string.picture_title)
        NavigationItems.Merge -> stringResource(id = R.string.merge_title)
        NavigationItems.Random -> stringResource(id = R.string.random_title)
        NavigationItems.Data -> stringResource(id = R.string.data_title)
        NavigationItems.Split -> stringResource(id = R.string.split)
        NavigationItems.Gradation -> stringResource(id = R.string.gradation_title)
        NavigationItems.Review -> stringResource(id = R.string.review_title)
        NavigationItems.Setting -> stringResource(id = R.string.setting_title)
        else -> throw IllegalArgumentException("Undefined NavigationItem: $item")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(CircleShape)
            .background(
                color = when {
                    isSelected -> when {
                        LocalIsDark.current -> {
                            MaterialTheme.colorScheme.primaryContainer.changeBrightness(1.2f)
                        }
                        else -> MaterialTheme.colorScheme.primaryContainer
                    }
                    else -> Color.Transparent
                },
            )
            .height(52.dp)
            .fillMaxWidth()
            .clickable { onItemClick(item) },
    ) {
        Image(
            painter = painterResource(id = item.icon!!),
            contentDescription = displayNavigationItemTitles,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(32.dp)
                .contentBrush(brush = primaryBrush()),
        )
        Row(
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = displayNavigationItemTitles,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}
