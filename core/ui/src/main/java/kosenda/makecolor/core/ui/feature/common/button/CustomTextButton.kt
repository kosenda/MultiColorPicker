package kosenda.makecolor.core.ui.feature.common.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.NoRippleInteractionSource
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun CustomTextButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.tertiary,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        TextButton(
            modifier = Modifier.height(48.dp),
            enabled = enabled,
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
            ),
            interactionSource = NoRippleInteractionSource(),
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
            )
        }
    }
}

@Preview
@Composable
fun PreviewCancelButton_Light() {
    MakeColorTheme(isDarkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            CustomTextButton(text = stringResource(id = R.string.cancel)) {}
        }
    }
}

@Preview
@Composable
fun PreviewCancelButton_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            CustomTextButton(text = stringResource(id = R.string.cancel)) {}
        }
    }
}
