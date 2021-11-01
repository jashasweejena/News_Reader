package com.example.newsreader.data.source

import com.example.newsreader.data.models.Article
import com.example.newsreader.data.source.local.ArticlesLocalDataSource
import com.example.newsreader.data.source.remote.ArticlesRemoteDataSource
import com.example.newsreader.data.source.scopes.Local
import com.example.newsreader.data.source.scopes.Remote
import com.example.newsreader.util.IOnlineChecker
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository
    @Inject constructor(
        @Local private val localDataSource: DataSource,
        @Remote private val remoteDataSource: DataSource,
        private val onlineChecker: IOnlineChecker
): DataSource {

    override fun getArticles(): Single<List<Article>> {
        val articles: Single<List<Article>> = localDataSource.getArticles()
        return articles.flatMap {
            // Fetch articles from RemoteDataSource only when LocalDataSource is empty / device is online
            if(onlineChecker.isOnline() || it.isEmpty()) {
                return@flatMap getFreshArticles()
            }

            // Else show articles from LocalDataSource
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