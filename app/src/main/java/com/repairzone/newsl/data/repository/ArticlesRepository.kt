package com.repairzone.newsl.data.repository

import androidx.paging.PagingData
import com.repairzone.newsl.data.local.room.entity.ArticlesType
import com.repairzone.newsl.data.network.base.Resource
import com.repairzone.newsl.data.network.model.Articles
import com.repairzone.newsl.data.network.model.ListArticlesResponse
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    suspend fun fetchHeadlines(country: String, category: String?): Flow<Resource<List<Articles>>>

    suspend fun fetchEverything(language: String, q: String? = null, sortBy: SortBy, page: Int, pageSize: Int): Flow<Resource<List<Articles>>>

    suspend fun fetchPagingEverything(language: String, q: String? = null, sortBy: SortBy): Flow<PagingData<Articles>>
}