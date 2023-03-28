package kosenda.makecolor.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.view.theme.MakeColorTheme

@Composable
fun InfoItemTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(horizontal = 16.dp),
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun InfoItemBody(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(horizontal = 16.dp),
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.tertiary,
        textAlign = TextAlign.Left,
    )
}

@Preview
@Composable
private fun PreviewInfoItem_Light() {
    MakeColorTheme(isDarkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Column {
                InfoItemTitle(text = "TITLE")
                InfoItemBody(text = "BODY")
            }
        }
    }
}

@Preview
@Composable
private fun PreviewInfoItem_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
            Column {
                InfoItemTitle(text = "TITLE")
                InfoItemBody(text = "BODY")
            }
        }
    }
}
