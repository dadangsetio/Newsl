package com.repairzone.newsl.data.repository

import com.repairzone.newsl.data.network.base.BaseAction

sealed interface ArticlesAction : BaseAction {
    data class GetHeadlines(val country: String, val category: String?): ArticlesAction
    data class GetEveryThing(val language: String, val q: String? = null, val sortBy: String, val page: Int = 1, val pageSize: Int = 10): ArticlesAction

    data class GetPagingEveryThing(val language: String, val q: String? = null, val sortBy: String): ArticlesAction
}