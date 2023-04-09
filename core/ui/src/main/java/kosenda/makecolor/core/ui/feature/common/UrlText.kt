package kosenda.makecolor.core.ui.feature.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.R
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun UrlText(url: String, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    Text(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .clickable(onClick = { uriHandler.openUri(uri = url) }),
        text = url,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.tertiary,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textDecoration = TextDecoration.Underline,
    )
}

@Preview
@Composable
private fun PreviewUrlText_Light() {
    MakeColorTheme(isDarkTheme = false) {
        UrlText(url = stringResource(id = R.string.review_url))
    }
}

@Preview
@Composable
private fun PreviewUrlText_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        UrlText(url = stringResource(id = R.string.review_url))
    }
}
