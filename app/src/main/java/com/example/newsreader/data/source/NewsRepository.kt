package com.example.newsreader.data.source

import com.example.newsreader.data.models.Article
import com.example.newsreader.data.source.local.ArticlesLocalDataSource
import com.example.newsreader.data.source.remote.ArticlesRemoteDataSource
import com.example.newsreader.data.source.scopes.Local
import com.example.newsreader.data.source.scopes.Remote
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository
    @Inject constructor(
        @Local private val localDataSource: DataSource,
        @Remote private val remoteDataSource: DataSource,
): DataSource {

    override fun getArticles(): Single<List<Article>> {
        // First get articles from remote
        // Save them into local
        // Return data from local


        //TODO: Data doesnt refresh after first fetch, fix it
        val articles: Single<List<Article>> = localDataSource.getArticles()
        return articles.flatMap {
            if(it.isEmpty()) {
                return@flatMap getFreshArticles()
            }
            Single.just(it)
        }

    }


    override fun saveArticles(articles: List<Article>) {
        localDataSource.saveArticles(articles)
    }

    override fun deleteAllArticles() {
        localDataSource.deleteAllArticles()
    }

    private fun getFreshArticles(): Single<List<Article>>{
        return remoteDataSource.getArticles()
            .doOnSuccess {
                saveArticles(it)
            }
    }
}