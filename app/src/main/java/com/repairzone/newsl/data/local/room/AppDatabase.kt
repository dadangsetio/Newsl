package com.repairzone.newsl.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.repairzone.newsl.data.local.room.dao.ArticlesDao
import com.repairzone.newsl.data.local.room.entity.ArticlesEntity

@Database(
    version = 2,
    entities = [
        ArticlesEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao

    companion object{
        @Volatile
        var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
            }

        }
    }
}