package com.example.newsreader.di.main

import androidx.lifecycle.ViewModel
import com.example.newsreader.di.annotations.ViewModelKey
import com.example.newsreader.ui.viewmodels.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NewsListViewModelBindingModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindsNewsViewModel(newsViewModel: NewsViewModel): ViewModel
}