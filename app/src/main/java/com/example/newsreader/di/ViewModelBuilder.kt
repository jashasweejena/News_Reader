package com.example.newsreader.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilder {
    @Binds
    abstract fun bindViewModelFactory(
        factory: DaggerViewModelFactory
    ): ViewModelProvider.Factory
}