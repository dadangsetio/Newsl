package com.repairzone.newsl.data.repository

import androidx.paging.PagingData
import com.repairzone.newsl.data.network.base.BaseEvent
import com.repairzone.newsl.data.network.base.Resource
import com.repairzone.newsl.data.network.model.Articles
import com.repairzone.newsl.data.network.model.ListArticlesResponse
import kotlinx.coroutines.flow.Flow

sealed interface ArticlesEvent : BaseEvent{
    data class HeadlineSuccess(val data: Flow<Resource<List<Articles>>>): ArticlesEvent
    data class ForYouSuccess(val data: Flow<Resource<List<Articles>>>): ArticlesEvent
    data class EverythingPagingSuccess(val data: Flow<PagingData<Articles>>): ArticlesEvent
    data class Failed(val throwable: Throwable): ArticlesEvent
}