package com.repairzone.newsl.di.module

import com.repairzone.newsl.data.repository.ArticlesRepository
import com.repairzone.newsl.data.repository.ArticlesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun articleRepo(impl: ArticlesRepositoryImpl): ArticlesRepository

}