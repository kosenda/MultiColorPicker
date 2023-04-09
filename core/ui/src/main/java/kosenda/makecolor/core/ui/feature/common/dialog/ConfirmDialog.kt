package kosenda.makecolor.core.ui.feature.common.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kosenda.makecolor.core.ui.R

@Composable
fun ConfirmDialog(text: String, onDismissRequest: () -> Unit, onClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(id = R.string.confirm),
                color = MaterialTheme.colorScheme.primary,
            )
        },
        text = {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.primary,
            )
        },
        confirmButton = {
            TextButton(onClick = onClick) {
                Text(
                    text = "OK",
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    )
}
