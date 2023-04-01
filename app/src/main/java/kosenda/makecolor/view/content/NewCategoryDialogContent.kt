package kosenda.makecolor.view.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.R
import kosenda.makecolor.model.Category
import kosenda.makecolor.view.component.button.TextButton
import kosenda.makecolor.view.component.button.TonalButton
import kosenda.makecolor.view.component.card.TitleCard
import kosenda.makecolor.view.component.textfield.EnterFiled
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun NewCategoryDialogContent(
    old: Category? = null,
    onClickNew: (Category) -> Unit,
    onClose: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var inputCategoryName by rememberSaveable { mutableStateOf(old?.name ?: "") }
    var canRegister by rememberSaveable { mutableStateOf(false) }
    val shape = RoundedCornerShape(16.dp)

    LaunchedEffect(inputCategoryName) {
        canRegister = inputCategoryName != ""
    }

    Scaffold(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .wrapContentHeight(),
        containerColor = Color.Transparent,
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .clip(shape = shape)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = shape,
                    )
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                focusManager.clearFocus()
                            },
                        )
                    },
            ) {
                TitleCard(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = stringResource(id = R.string.category),
                    painter = painterResource(id = R.drawable.baseline_category_24),
                )

                EnterFiled(
                    text = inputCategoryName,
                    onValueChange = { new -> inputCategoryName = new },
                    focusManager = focusManager,
                    hint = stringResource(id = R.string.enter_new_category),
                    singleLine = true,
                    maxLines = 1,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        text = stringResource(id = R.string.cancel),
                        onClick = onClose,
                    )
                    TonalButton(
                        text = when (old) {
                            null -> stringResource(id = R.string.add)
                            else -> stringResource(id = R.string.change)
                        },
                        enabled = canRegister,
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
                        onClick = {
                            onClickNew(
                                when (old) {
                                    null -> Category(name = inputCategoryName, size = 0)
                                    else -> old.copy(name = inputCategoryName)
                                },
                            )
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
private fun PreviewAddCategoryDialogContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize()) {
            NewCategoryDialogContent(onClickNew = {}, onClose = {})
        }
    }
}

@Preview
@Composable
private fun PreviewAddCategoryDialogContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize()) {
            NewCategoryDialogContent(onClickNew = {}, onClose = {})
        }
    }
}
