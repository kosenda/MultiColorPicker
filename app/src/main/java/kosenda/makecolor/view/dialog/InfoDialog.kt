package kosenda.makecolor.view.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kosenda.makecolor.view.content.InfoDialogContent

@Composable
fun InfoDialog(onClose: () -> Unit) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        InfoDialogContent(onClose = onClose)
    }
}
