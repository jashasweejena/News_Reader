package com.example.newsreader.data.source.remote

import com.example.newsreader.data.models.Article
import com.example.newsreader.data.models.NewsResponse
import com.example.newsreader.data.source.DataSource
import com.example.newsreader.util.Constants
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ArticlesRemoteDataSource
    @Inject constructor(private val newsApiService: NewsApiService) : DataSource {

    override fun getArticles(): Single<List<Article>> {
        return newsApiService.getNews("in", Constants.API_KEY)
            .flatMap { response: NewsResponse ->
                Observable.fromIterable(response.articles).toList()
            }
    }

    // No need to provide implementations for these
    // methods here as they are implemented in ArticlesLocalDataSource
    override fun saveArticles(articles: List<Article>) = Unit
    override fun deleteAllArticles() = Unit
}