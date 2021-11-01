package com.example.newsreader.di

import com.example.newsreader.MainActivity
import com.example.newsreader.data.source.remote.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import retrofit2.Retrofit

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [
        NewsListFragmentBindingModule::class,
        FragmentBuilder::class,
        ViewModelBuilder::class,
        MainActivityModule::class
    ])
    abstract fun bindMainActivity(): MainActivity
}

@Module
class MainActivityModule() {

    @Provides
    fun providesNewsApiService(retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)
}