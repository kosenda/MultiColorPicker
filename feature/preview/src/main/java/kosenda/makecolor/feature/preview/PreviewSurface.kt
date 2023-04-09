package kosenda.makecolor.feature.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kosenda.makecolor.core.ui.feature.theme.backgroundBrush

@Composable
fun PreviewSurface(
    content: @Composable () -> Unit,
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush()),
    ) {
        Column {
            content()
        }
    }
}
