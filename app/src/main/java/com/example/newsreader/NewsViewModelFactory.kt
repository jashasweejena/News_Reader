package com.example.newsreader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsreader.data.source.NewsRepository
import javax.inject.Inject

class NewsViewModelFactory @Inject constructor(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }

}