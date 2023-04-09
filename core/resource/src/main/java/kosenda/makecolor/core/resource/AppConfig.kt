package kosenda.makecolor.core.resource

data class AppConfig(
    val adApplicationId: String,
    val adUnitId: String,
    val applicationId: String,
    val buildType: String,
    val isDebug: Boolean,
    val versionCode: Int,
    val versionName: String,
)