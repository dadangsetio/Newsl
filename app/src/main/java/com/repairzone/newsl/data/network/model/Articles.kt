package com.repairzone.newsl.data.network.model

import android.service.autofill.OnClickAction
import com.repairzone.newsl.data.local.room.entity.ArticlesEntity
import com.repairzone.newsl.data.local.room.entity.ArticlesType
import com.repairzone.newsl.ui.base.OnClickActionModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Articles(
    @SerialName("source") var source: Source? = Source(),
    @SerialName("author") var author: String? = null,
    @SerialName("title") var title: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("url") var url: String? = null,
    @SerialName("urlToImage") var urlToImage: String? = null,
    @SerialName("publishedAt") var publishedAt: String? = null,
    @SerialName("content") var content: String? = null
): OnClickActionModel()

fun Articles.toEntity(type: ArticlesType) = ArticlesEntity(
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    id = source?.id,
    name = source?.name,
    type = type
)
