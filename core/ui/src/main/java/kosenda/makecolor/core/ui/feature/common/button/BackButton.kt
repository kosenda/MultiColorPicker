package kosenda.makecolor.core.ui.feature.common.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.resource.R

@Composable
fun BackButton(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .size(48.dp)
            .clickable(onClick = onClickBack),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "back screen",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
        )
    }
}
