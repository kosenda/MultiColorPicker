package kosenda.makecolor.core.ui.feature.common.button

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.R
import kosenda.makecolor.core.util.shareIntent
import kosenda.makecolor.core.ui.feature.theme.contentBrush
import kosenda.makecolor.core.ui.feature.theme.primaryBrush

@Composable
fun ShareIcon(hex1: String?, hex2: String?) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp - 16.dp
    val metrics = context.resources.displayMetrics
    val btmHeight = (screenWidth + 1.dp).value * metrics.density
    val btmWidth = (250.dp + 1.dp).value * metrics.density
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val reviewURL = stringResource(id = R.string.review_url)

    IconButton(
        modifier = Modifier.size(48.dp).contentBrush(brush = primaryBrush()),
        onClick = {
            shareIntent(
                hex1 = hex1,
                hex2 = hex2,
                btmHeight = btmHeight,
                btmWidth = btmWidth,
                density = density,
                layoutDirection = layoutDirection,
                context = context,
                reviewURL = reviewURL,
            )
        },
    ) {
        Icon(
            imageVector = Icons.Outlined.Share,
            contentDescription = "share",
            modifier = Modifier.size(28.dp),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}
