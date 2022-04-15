package com.repairzone.newsl.data.repository

import com.repairzone.newsl.data.ArticlesPagingSource
import com.repairzone.newsl.data.network.base.BaseDataSource
import com.repairzone.newsl.data.network.service.ApiServices
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesDataStore @Inject constructor(
    private val apiServices: ApiServices
) : BaseDataSource() {

    suspend fun fetchHeadlines(contry: String, category: String?) = apiCall {
        apiServices.getHeadlines(contry, category)
    }

    suspend fun fetchEverything(
        language: String,
        q: String?,
        sortBy: String,
        page: Int,
        pageSize: Int
    ) = apiCall {
        apiServices.getEverything(language, q, sortBy, page, pageSize)
    }


    fun fetchPagingEverything(
        language: String,
        q: String?,
        sortBy: String,
    ) = ArticlesPagingSource(apiServices, language, q, sortBy)
}