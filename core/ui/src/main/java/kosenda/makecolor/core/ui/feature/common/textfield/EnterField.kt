package kosenda.makecolor.core.ui.feature.common.textfield

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kosenda.makecolor.view.component.ClearTextIcon

@Composable
fun EnterFiled(
    text: String,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
    hint: String,
    singleLine: Boolean = false,
    maxLines: Int = 10,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        keyboardActions = KeyboardActions { focusManager.clearFocus() },
        placeholder = {
            Text(
                text = hint,
                color = Color.Gray,
            )
        },
        singleLine = singleLine,
        maxLines = maxLines,
        modifier = Modifier
            .imePadding()
            .padding(horizontal = 8.dp)
            .padding(bottom = 16.dp)
            .defaultMinSize(96.dp)
            .fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = MaterialTheme.colorScheme.primary,
        ),
        trailingIcon = {
            ClearTextIcon(onClick = { onValueChange("") })
        },
    )
}
