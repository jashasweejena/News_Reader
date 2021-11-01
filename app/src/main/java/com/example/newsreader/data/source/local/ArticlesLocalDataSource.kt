package com.example.newsreader.data.source.local

import com.example.newsreader.data.models.Article
import com.example.newsreader.data.source.DataSource
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// TODO: Add check for when internet connectivity is back and then refetch stuff
class ArticlesLocalDataSource
    @Inject constructor(private val articlesDao: ArticlesDao): DataSource {
    override fun getArticles(): Single<List<Article>> =
        articlesDao.getAllArticles()

    override fun saveArticles(articles: List<Article>) {
        Completable.fromRunnable {
            articles.forEach { article ->
                // Use copy function to create a copy of 'article'
                // to set 'id' of Article object before saving it into room
                articlesDao.insertArticles(article.copy(id = System.currentTimeMillis()))
            }
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun deleteAllArticles() {
        Completable.fromRunnable {
            articlesDao.deleteArticles()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

}