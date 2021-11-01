package com.example.newsreader.data.source

import com.example.newsreader.data.models.Article
import io.reactivex.Single

interface DataSource {
    fun getArticles(): Single<List<Article>>
    fun saveArticles(articles: List<Article>)
    fun deleteAllArticles()
}