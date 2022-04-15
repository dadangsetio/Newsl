package com.repairzone.newsl.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repairzone.newsl.data.local.room.entity.ArticlesEntity
import com.repairzone.newsl.data.local.room.entity.ArticlesType
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(articlesEntity: ArticlesEntity)

    @Query("SELECT * From articles")
    fun getAll(): Flow<List<ArticlesEntity>>

    @Query("SELECT * From articles WHERE type == :type")
    fun getAll(type: ArticlesType): Flow<List<ArticlesEntity>>

    @Query("SELECT * FROM articles WHERE id == :id")
    fun get(id: String): Flow<List<ArticlesEntity>>

    @Query("DELETE FROM articles")
    suspend fun deleteAll()

    @Query("DELETE FROM articles WHERE id == :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM articles WHERE type == :type")
    suspend fun deleteAll(type: ArticlesType)
}