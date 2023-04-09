package kosenda.makecolor.view.content

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kosenda.makecolor.R
import kosenda.makecolor.core.ui.feature.common.button.CustomButton
import kosenda.makecolor.core.ui.feature.common.card.TitleCard
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme

@Composable
fun LicensesContent() {
    val context = LocalContext.current
    val buttonText = stringResource(id = R.string.oss_licenses)
    TitleCard(
        text = stringResource(id = R.string.licenses_title),
        painter = painterResource(id = R.drawable.outline_info_24),
    )
    CustomButton(
        text = buttonText,
        onClick = {
            val intent = Intent(context, OssLicensesMenuActivity::class.java)
            intent.putExtra("title", buttonText)
            ContextCompat.startActivity(context, intent, null)
        },
    )
}

@Preview
@Composable
private fun PreviewLicensedContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        LicensesContent()
    }
}

@Preview
@Composable
private fun PreviewLicensedContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        LicensesContent()
    }
}
