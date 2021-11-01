package com.example.newsreader.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.newsreader.data.models.Article
import com.example.newsreader.data.source.NewsRepository
import io.reactivex.Single
import javax.inject.Inject

class NewsViewModel
@Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    fun getNewsArticles(): Single<List<Article>> = newsRepository.getNewsArticles()
}