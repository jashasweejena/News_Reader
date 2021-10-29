package com.example.newsreader

import androidx.lifecycle.ViewModel
import com.example.newsreader.data.models.Article
import com.example.newsreader.data.source.NewsRepository
import io.reactivex.Single

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    fun getNewsArticles(): Single<List<Article>> = newsRepository.getNewsArticles()
}