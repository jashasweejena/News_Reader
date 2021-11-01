package com.example.newsreader.di.main

import android.app.Application
import androidx.room.Room
import com.example.newsreader.data.source.DataSource
import com.example.newsreader.data.source.local.ArticlesDao
import com.example.newsreader.data.source.local.ArticlesDatabase
import com.example.newsreader.data.source.local.ArticlesLocalDataSource
import com.example.newsreader.data.source.remote.ArticlesRemoteDataSource
import com.example.newsreader.data.source.remote.NewsApiService
import com.example.newsreader.data.source.scopes.Local
import com.example.newsreader.data.source.scopes.Remote
import com.example.newsreader.util.Constants
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    @Local
    fun providesLocalDataSource(
        articlesDao: ArticlesDao
    ): DataSource {
        return ArticlesLocalDataSource(articlesDao)
    }

    @Provides
    @Remote
    fun providesRemoteDataSource(
        newsApiService: NewsApiService
    ): DataSource {
        return ArticlesRemoteDataSource(newsApiService)
    }

    @Provides
    fun providesDb(application: Application): ArticlesDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            ArticlesDatabase::class.java,
            Constants.ARTICLES_ROOM_DB
        )
            .build()
    }

    @Provides
    fun providesArticlesDao(articlesDatabase: ArticlesDatabase): ArticlesDao {
        return articlesDatabase.articlesDao()
    }
}