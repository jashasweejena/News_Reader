package com.example.newsreader.di

import com.example.newsreader.ui.activities.MainActivity
import com.example.newsreader.data.source.remote.NewsApiService
import com.example.newsreader.di.main.NewsListFragmentBindingModule
import com.example.newsreader.di.main.NewsListViewModelBindingModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [
        NewsListFragmentBindingModule::class,
        NewsListViewModelBindingModule::class,
        MainActivityModule::class,
    ])
    abstract fun bindMainActivity(): MainActivity
}

@Module
class MainActivityModule {

    @Provides
    fun providesNewsApiService(retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)
}