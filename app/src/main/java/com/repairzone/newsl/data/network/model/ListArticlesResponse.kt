package com.repairzone.newsl.data.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListArticlesResponse(
    @SerialName("status") var status: String? = null,
    @SerialName("totalResults") var totalResults: Int? = null,
    @SerialName("articles") var articles: ArrayList<Articles> = arrayListOf()
)