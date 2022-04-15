package com.repairzone.newsl.data.local.room

import com.repairzone.newsl.data.local.room.dao.ArticlesDao
import com.repairzone.newsl.data.local.room.entity.ArticlesEntity
import com.repairzone.newsl.data.local.room.entity.ArticlesType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesRoomSource @Inject constructor(
    private val articlesDao: ArticlesDao
) {
    fun load() = articlesDao.getAll()

    fun load(type: ArticlesType) = articlesDao.getAll(type)

    fun articles(id: String) = articlesDao.get(id)

    suspend fun save(articlesEntity: ArticlesEntity) {
        articlesDao.save(articlesEntity)
    }

    suspend fun save(list: List<ArticlesEntity>){
        list.forEach {
            articlesDao.save(it)
        }
    }

    suspend fun clear() = articlesDao.deleteAll()

    suspend fun clear(id: String) = articlesDao.delete(id)

    suspend fun clear(id: ArticlesType) = articlesDao.deleteAll(id)
}