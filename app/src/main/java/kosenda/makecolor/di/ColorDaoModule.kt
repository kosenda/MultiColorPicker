package kosenda.makecolor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kosenda.makecolor.model.ColorDao
import kosenda.makecolor.model.ColorDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ColorDaoModule {
    @Provides
    @Singleton
    fun provideColorDao(database: ColorDatabase): ColorDao = database.colorDao()
}
