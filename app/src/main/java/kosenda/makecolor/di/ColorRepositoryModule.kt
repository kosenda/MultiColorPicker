package kosenda.makecolor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kosenda.makecolor.model.repository.ColorRepository
import kosenda.makecolor.model.repository.ColorRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ColorRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindColorRepository(impl: ColorRepositoryImpl): ColorRepository
}
