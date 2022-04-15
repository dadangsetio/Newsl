package com.repairzone.newsl.data.network.service

import com.repairzone.newsl.data.network.model.ListArticlesResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.*

interface ApiServices {

    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String,
        @Query("category") category: String?,
    ): Response<ListArticlesResponse>

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("language") language: String,
        @Query("q") q: String?,
        @Query("sortBy") sortBy: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ListArticlesResponse>

    companion object Factory {
        operator fun invoke(retrofit: Retrofit): ApiServices = retrofit.create()

        const val CUSTOM_HEADER = "@"
        const val NO_AUTH = "NoAuth"
        const val POS_AUTH = "PosAuth"
    }
}