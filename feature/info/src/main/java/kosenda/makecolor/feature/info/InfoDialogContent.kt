package kosenda.makecolor.feature.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.resource.R
import kosenda.makecolor.core.ui.feature.common.button.CustomIconButton
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.ui.feature.theme.backgroundBrush

@Composable
fun InfoDialogContent(onClose: () -> Unit) {
    Scaffold(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(brush = backgroundBrush()),
        containerColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .displayCutoutPadding()
                .fillMaxWidth(),
        ) {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                CustomIconButton(
                    modifier = Modifier.padding(all = 8.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_close_24),
                    contentDescription = "close dialog",
                    onClick = onClose,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
            ) {
                AppInfoContent()
                DeveloperInfoContent()
                LicensesContent()
                PrivacyPolicyContent()
                Spacer(modifier = Modifier.height(88.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewInfoDialogContent_Light() {
    MakeColorTheme(isDarkTheme = false) {
        InfoDialogContent {}
    }
}

@Preview
@Composable
fun PreviewInfoDialogContent_Dark() {
    MakeColorTheme(isDarkTheme = true) {
        InfoDialogContent {}
    }
}
