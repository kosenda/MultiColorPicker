package kosenda.makecolor.view.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kosenda.makecolor.BuildConfig

@Composable
fun GoogleAd() {
    AndroidView(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .background(Color.Transparent),
        factory = {
            val adView = AdView(it)
            val displayMetrics = it.resources.displayMetrics
            val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
            adView.setAdSize(
                AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(it, width),
            )
            adView.adUnitId = BuildConfig.adUnitId
            adView.loadAd(AdRequest.Builder().build())
            adView
        },
    )
}
