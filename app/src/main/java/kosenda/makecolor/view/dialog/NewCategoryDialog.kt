package kosenda.makecolor.view.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kosenda.makecolor.model.Category
import kosenda.makecolor.view.content.NewCategoryDialogContent

@Composable
fun NewCategoryDialog(
    oldCategories: Category? = null,
    onClickNew: (Category) -> Unit,
    onClose: () -> Unit,
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        NewCategoryDialogContent(
            old = oldCategories,
            onClickNew = onClickNew,
            onClose = onClose,
        )
    }
}
