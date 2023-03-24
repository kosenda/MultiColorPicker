package kosenda.makecolor.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kosenda.makecolor.model.ColorDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ColorDatabaseModule {
    @Provides
    @Singleton
    fun provideColorDatabase(@ApplicationContext context: Context): ColorDatabase {
        return ColorDatabase.getInstance(context = context)
    }
}
