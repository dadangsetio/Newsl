package com.repairzone.newsl.di.module

import android.content.Context
import androidx.room.Room
import com.repairzone.newsl.data.local.room.AppDatabase
import com.repairzone.newsl.utils.AppDispatchers
import com.repairzone.newsl.utils.AppDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun appDispatcher(impl: AppDispatchersImpl): AppDispatchers

    internal companion object{
        @Singleton
        @Provides
        fun provideAppDatabse(
            @ApplicationContext context: Context
        ): AppDatabase =
            AppDatabase.INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "data-cache.db"
                ).fallbackToDestructiveMigration()
                    .build()

                AppDatabase.INSTANCE = instance
                instance
            }
    }
}