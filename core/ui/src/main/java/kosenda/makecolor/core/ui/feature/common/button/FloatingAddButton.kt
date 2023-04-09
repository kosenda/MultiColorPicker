package kosenda.makecolor.core.ui.feature.common.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.R

@Composable
fun FloatingAddButton(onClick: () -> Unit, scrollState: ScrollState) {
    val visible by remember {
        derivedStateOf { scrollState.value == 0 }
    }

    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = Modifier.padding(bottom = 16.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = stringResource(id = R.string.add),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(48.dp),
        )
        AnimatedVisibility(
            visible = visible,
        ) {
            Text(
                text = stringResource(id = R.string.add),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}
