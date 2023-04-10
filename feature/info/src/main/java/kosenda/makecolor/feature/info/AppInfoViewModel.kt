package kosenda.makecolor.feature.info

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kosenda.makecolor.core.resource.AppConfig
import javax.inject.Inject

abstract class AppInfoViewModel : ViewModel() {
    abstract fun getAppConfig(): AppConfig
}

@HiltViewModel
class AppInfoViewModelImpl @Inject constructor(
    private val appConfig: AppConfig
): AppInfoViewModel() {
    override fun getAppConfig() = appConfig
}

class PreviewAppInfoViewModel : AppInfoViewModel() {
    override fun getAppConfig() = AppConfig(
        adApplicationId = "adApplicationId",
        adUnitId = "adUnitId",
        applicationId = "applicationId",
        buildType = "buildType",
        isDebug = true,
        versionCode = 1,
        versionName = "versionName",
    )
}