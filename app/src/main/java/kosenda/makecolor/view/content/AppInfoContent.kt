package kosenda.makecolor.view.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.BuildConfig
import kosenda.makecolor.R
import kosenda.makecolor.core.ui.feature.common.InfoItemBody
import kosenda.makecolor.core.ui.feature.common.InfoItemTitle
import kosenda.makecolor.core.ui.feature.common.UrlText
import kosenda.makecolor.core.ui.feature.common.card.TitleCard
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun AppInfoContent() {
    TitleCard(
        text = stringResource(id = R.string.app_info_title),
        painter = painterResource(id = R.drawable.outline_info_24),
    )
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_monochrome),
                contentDescription = "convert",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(all = 16.dp)
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.surface),
            )
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
            ) {
                InfoItemTitle(
                    text = stringResource(id = R.string.app_name_title),
                    modifier = Modifier.padding(bottom = 4.dp),
                )
                InfoItemBody(text = stringResource(id = R.string.app_name))

                InfoItemTitle(
                    text = stringResource(id = R.string.version_title),
                    modifier = Modifier.padding(bottom = 4.dp, top = 16.dp),
                )
                InfoItemBody(text = BuildConfig.VERSION_NAME)

                InfoItemTitle(
                    text = stringResource(id = R.string.google_play),
                    modifier = Modifier.padding(bottom = 4.dp, top = 16.dp),
                )
                UrlText(
                    url = stringResource(id = R.string.review_url),
                )

                InfoItemTitle(
                    text = stringResource(id = R.string.github),
                    modifier = Modifier.padding(bottom = 4.dp, top = 16.dp),
                )
                UrlText(
                    url = stringResource(id = R.string.github_url),
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAppInfoContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        AppInfoContent()
    }
}

@Preview
@Composable
private fun PreviewAppInfoContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        AppInfoContent()
    }
}
