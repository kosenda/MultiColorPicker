package kosenda.makecolor.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kosenda.makecolor.core.database.ColorDao
import kosenda.makecolor.core.database.ColorDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ColorDaoModule {
    @Provides
    @Singleton
    fun provideColorDao(database: ColorDatabase): ColorDao = database.colorDao()
}
