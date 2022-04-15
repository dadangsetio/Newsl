package com.repairzone.newsl.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.repairzone.newsl.data.network.model.Articles
import com.repairzone.newsl.data.network.model.Source
import kotlinx.serialization.SerialName

@Entity(tableName = "Articles")
data class ArticlesEntity(
    @PrimaryKey(autoGenerate = true)
    val idArticles: Int? = null,
    val id: String?,
    val name: String?,
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String?,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String? ,
    var type: ArticlesType? = null,
)

fun ArticlesEntity.toData() = Articles(
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content,
    source = Source(
        id,
        name
    )
)

enum class ArticlesType {
    Headlines,
    Popular,
    Latest,
}
