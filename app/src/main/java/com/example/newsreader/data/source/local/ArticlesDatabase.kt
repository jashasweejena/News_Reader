package com.example.newsreader.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsreader.data.models.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticlesDatabase : RoomDatabase(){
    abstract fun articlesDao(): ArticlesDao
}