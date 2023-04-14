package kosenda.makecolor.core.ui.feature.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.resource.R

@Composable
fun ClearTextIcon(onClick: () -> Unit) {
    val buttonScaleState = rememberButtonScaleState()
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .size(48.dp)
            .scale(scale = buttonScaleState.animationScale.value)
            .clickable(
                interactionSource = buttonScaleState.interactionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = R.drawable.outline_cancel_24),
            contentDescription = "clear text",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.surfaceTint),
        )
    }
}
