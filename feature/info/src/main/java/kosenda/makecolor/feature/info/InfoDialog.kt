package kosenda.makecolor.feature.info

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun InfoDialog(onClose: () -> Unit) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        BackHandler(onBack = onClose)
        InfoDialogContent(onClose = onClose)
    }
}
