package com.example.newsreader.data.source.local

import android.util.Log
import com.example.newsreader.data.models.Article
import com.example.newsreader.data.source.DataSource
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticlesLocalDataSource
    @Inject constructor(private val articlesDao: ArticlesDao): DataSource {
    override fun getArticles(): Single<List<Article>> =
        articlesDao.getAllArticles()

    override fun saveArticles(articles: List<Article>) {
        Completable.fromRunnable {
            articles.forEach { article: Article? ->
                // Use copy function to create a copy of 'article'
                // to set 'id' of Article object before saving it into room
                article?.let {
                    articlesDao.insertArticles(article.copy(id = System.currentTimeMillis()))
                }
            }
        }.subscribeOn(Schedulers.io())
            .subscribe({})
            {   e: Throwable ->
                Log.d("ArticlesLocalDataSource", "saveArticles: " + e.message)
            }
    }

    override fun deleteAllArticles() {
        Completable.fromRunnable {
            articlesDao.deleteArticles()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

}