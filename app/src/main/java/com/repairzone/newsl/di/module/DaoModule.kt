package com.repairzone.newsl.di.module

import com.repairzone.newsl.data.local.room.AppDatabase
import com.repairzone.newsl.data.local.room.dao.ArticlesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {

    @Provides
    fun provideBannersDao(appDatabase: AppDatabase): ArticlesDao =
        appDatabase.articlesDao()
}