package kosenda.makecolor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kosenda.makecolor.app.BuildConfig
import kosenda.makecolor.core.resource.AppConfig

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {
    @Provides
    fun provideAppConfig(): AppConfig = AppConfig(
        adApplicationId = BuildConfig.adApplicationId,
        adUnitId = BuildConfig.adUnitId,
        applicationId = BuildConfig.APPLICATION_ID,
        buildType = BuildConfig.BUILD_TYPE,
        isDebug = BuildConfig.DEBUG,
        versionCode = BuildConfig.VERSION_CODE,
        versionName = BuildConfig.VERSION_NAME,
    )
}
