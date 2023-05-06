package kosenda.makecolor.core.ui.feature.common.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.extension.noRippleClickable
import kosenda.makecolor.core.ui.feature.common.rememberButtonScaleState
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun SpinnerCard(
    modifier: Modifier = Modifier,
    selectedText: String,
    categoryName: String,
    onSelectedChange: (Int) -> Unit,
    displayItemList: Array<String>,
) {
    var expanded by remember { mutableStateOf(false) }
    val buttonScaleState = rememberButtonScaleState()

    Row {
        Card(
            modifier = modifier
                .padding(vertical = 8.dp)
                .scale(scale = buttonScaleState.animationScale.value)
                .shadow(shape = RoundedCornerShape(12.dp), elevation = 2.dp)
                .noRippleClickable(
                    interactionSource = buttonScaleState.interactionSource,
                    onClick = { expanded = true },
                ),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            ConversionTypeSpinnerCardContent(
                selectedText = selectedText,
                categoryName = categoryName,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 8.dp, y = 4.dp),
        ) {
            displayItemList.mapIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        onSelectedChange(index)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun ConversionTypeSpinnerCardContent(
    selectedText: String,
    categoryName: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(all = 8.dp),
    ) {
        Column {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Text(
                text = selectedText,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = "spinner",
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(28.dp),
        )
    }
}

@Preview
@Composable
private fun PreviewSpinnerCard_Light() {
    MakeColorTheme(isDarkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            SpinnerCard(
                selectedText = stringArrayResource(id = R.array.square_circle)[0],
                categoryName = stringResource(id = R.string.picker),
                onSelectedChange = { },
                displayItemList = stringArrayResource(id = R.array.square_circle),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSpinnerCard_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            SpinnerCard(
                selectedText = stringArrayResource(id = R.array.square_circle)[0],
                categoryName = stringResource(id = R.string.picker),
                onSelectedChange = {},
                displayItemList = stringArrayResource(id = R.array.square_circle),
            )
        }
    }
}
