package kosenda.makecolor.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun SelectLanguageDialog(
    onClickClose: () -> Unit,
) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        SelectLanguageDialogContent(onClickClose = onClickClose)
    }
}
