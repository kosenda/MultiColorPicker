package kosenda.makecolor.view.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kosenda.makecolor.view.content.SelectLanguageDialogContent

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
