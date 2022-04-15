package com.repairzone.newsl.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.repairzone.newsl.data.local.room.ArticlesRoomSource
import com.repairzone.newsl.data.local.room.entity.ArticlesType
import com.repairzone.newsl.data.local.room.entity.toData
import com.repairzone.newsl.data.network.base.Resource
import com.repairzone.newsl.data.network.model.Articles
import com.repairzone.newsl.data.network.model.ListArticlesResponse
import com.repairzone.newsl.data.network.model.toEntity
import com.repairzone.newsl.utils.AppDispatchers
import com.repairzone.newsl.utils.ext.performGet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val appDispatchers: AppDispatchers,
    private val dataStore: ArticlesDataStore,
    private val roomSource: ArticlesRoomSource
) : ArticlesRepository {

    private fun articlesListFlow(type: ArticlesType)  = roomSource.load(type).map { list ->
        list.map {
            it.toData()
        }
    }.distinctUntilChanged()

    private fun articlesHeadlinesListFlow()  = roomSource.load().map { list ->
        list.map {
            it.toData()
        }
    }.distinctUntilChanged()

    override suspend fun fetchHeadlines(
        country: String,
        category: String?
    ) = withContext(appDispatchers.io){
        performGet(
            databaseQuery = {
                articlesHeadlinesListFlow()
            },
            networkDataSource = {
                dataStore.fetchHeadlines(country, category)
            },
            saveResult = { resource ->
                roomSource.clear(ArticlesType.Headlines)
                resource?.let { listArticlesResponse ->
                    val data = listArticlesResponse.articles.map {
                        it.toEntity(type = ArticlesType.Headlines)
                    }
                    roomSource.save(data)
                }
            }
        )
    }

    override suspend fun fetchEverything(
        language: String,
        q: String?,
        sortBy: SortBy,
        page: Int,
        pageSize: Int
    ) = withContext(appDispatchers.io){
        val type = when(sortBy){
            SortBy.popularity -> ArticlesType.Popular
            else -> ArticlesType.Latest
        }
        performGet(
            databaseQuery = { articlesListFlow(type) },
            networkDataSource = {
                dataStore.fetchEverything(language, q, sortBy.name, page, pageSize)
            },
            saveResult = { resource ->
                roomSource.clear(type)
                resource?.let { listArticlesResponse ->
                    val local = listArticlesResponse.articles.map {
                        it.toEntity(type)
                    }
                    roomSource.save(local)
                }
            }
        )
    }

    override suspend fun fetchPagingEverything(language: String, q: String?, sortBy: SortBy) = withContext(appDispatchers.io){
        Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                dataStore.fetchPagingEverything(language, q, sortBy.name)
            }
        ).flow
    }
}

enum class SortBy {
    popularity,
    relevancy,
    publishedAt
}