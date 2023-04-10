package kosenda.makecolor.core.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kosenda.makecolor.core.data.database.ColorDatabase
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
