package kosenda.makecolor.core.ui.feature.common.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.model.data.StringResource
import kosenda.makecolor.core.ui.feature.common.ClearTextIcon

@Composable
fun ColorTextField(
    text: String,
    inputText: String,
    isHex: Boolean = false,
    onValueChange: (String) -> StringResource?,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val density = LocalDensity.current
    var offsetErrorTextX by rememberSaveable { mutableStateOf(0f) }
    var isError by rememberSaveable { mutableStateOf(false) }
    var errorText: String by rememberSaveable { mutableStateOf("") }
    fun StringResource?.toString(): String = this?.getString(context = context) ?: ""

    LaunchedEffect(errorText) {
        isError = errorText.isNotEmpty()
    }

    LaunchedEffect(inputText) {
        errorText = onValueChange(inputText).toString()
    }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
            OutlinedTextField(
                value = inputText,
                onValueChange = { new -> errorText = onValueChange(new).toString() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
                    .onGloballyPositioned {
                        offsetErrorTextX = it.positionInParent().x / density.density
                    },
                trailingIcon = {
                    ClearTextIcon(onClick = { errorText = onValueChange("").toString() })
                },
                keyboardActions = KeyboardActions { focusManager.clearFocus() },
                isError = isError,
                visualTransformation = UppercaseVisualTransformation(),
                keyboardOptions = when {
                    isHex -> KeyboardOptions.Default
                    else -> KeyboardOptions(keyboardType = KeyboardType.Number)
                },
                singleLine = true,
                maxLines = 1,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
        if (errorText.isNotEmpty()) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .offset(x = offsetErrorTextX.dp),
            )
        }
    }
}
