package kosenda.makecolor.core.ui.feature.common.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
): Modifier = composed {
    clickable(
        interactionSource = remember { interactionSource },
        indication = null,
        onClick = { onClick() },
    )
}