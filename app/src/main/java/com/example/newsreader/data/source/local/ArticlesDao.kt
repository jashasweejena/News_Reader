package com.example.newsreader.data.source.local

import androidx.room.*
import com.example.newsreader.data.models.Article
import io.reactivex.Single

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(vararg articles: Article)

    @Update
    fun updateArticles(vararg articles: Article)

    @Delete
    fun deleteArticles(vararg articles: Article)

    @Query("SELECT * FROM Articles")
    fun getAllArticles(): Single<List<Article>>
}