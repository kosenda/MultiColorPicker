package kosenda.makecolor.feature.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kosenda.makecolor.core.ui.feature.common.button.BottomCloseButton
import kosenda.makecolor.core.ui.feature.theme.MakeColorTheme
import kosenda.makecolor.core.ui.feature.theme.backgroundBrush

@Composable
fun InfoDialogContent(onClose: () -> Unit) {
    Scaffold(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(brush = backgroundBrush()),
        bottomBar = {
            BottomCloseButton(onClick = onClose)
        },
        containerColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            AppInfoContent()
            DeveloperInfoContent()
            LicensesContent()
            Spacer(modifier = Modifier.height(48.dp))
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
