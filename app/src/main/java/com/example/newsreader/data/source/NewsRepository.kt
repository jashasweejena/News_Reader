package com.example.newsreader.data.source

import com.example.newsreader.data.models.Article
import com.example.newsreader.data.source.remote.ArticlesRemoteDataSource
import io.reactivex.Single

class NewsRepository(
    private val remoteDataSource: ArticlesRemoteDataSource
) {
    fun getNewsArticles(): Single<List<Article>> = remoteDataSource.getNewsArticles()
}