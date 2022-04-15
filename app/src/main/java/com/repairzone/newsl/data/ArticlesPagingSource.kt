package com.repairzone.newsl.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.repairzone.newsl.data.network.base.BaseDataSource
import com.repairzone.newsl.data.network.model.Articles
import com.repairzone.newsl.data.network.service.ApiServices
import javax.inject.Inject

class ArticlesPagingSource(private var apiServices: ApiServices, private val language: String, private val q: String?, private val sortBy: String) : PagingSource<Int, Articles>(){

    override fun getRefreshKey(state: PagingState<Int, Articles>) = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Articles> {
        val pageNumber = params.key ?: 1
        return try {
            val response = apiServices.getEverything(language, q, sortBy, pageNumber, 10)

            val body = response.body()
            val data = body?.articles
            var nextPageNumber: Int? = null
            if (pageNumber < 10){
                nextPageNumber = pageNumber + 1
            }
            LoadResult.Page(
                nextKey = nextPageNumber,
                prevKey = null,
                data = data.orEmpty()
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}