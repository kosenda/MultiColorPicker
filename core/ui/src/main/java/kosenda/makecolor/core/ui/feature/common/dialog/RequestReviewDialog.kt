package kosenda.makecolor.core.ui.feature.common.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kosenda.makecolor.core.resource.R

@Composable
fun RequestReviewDialog(onCancel: () -> Unit, onClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(
                text = stringResource(id = R.string.request_review_title),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.request_review_body),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
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
            TextButton(onClick = onCancel) {
                Text(
                    text = stringResource(id = R.string.request_review_cancel),
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    )
}
