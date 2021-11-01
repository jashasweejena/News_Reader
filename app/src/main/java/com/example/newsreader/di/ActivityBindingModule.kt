package com.example.newsreader.di

import com.example.newsreader.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = [
        NewsListFragmentBindingModule::class,
        FragmentBuilder::class,
        ViewModelBuilder::class
    ])
    abstract fun bindMainActivity(): MainActivity
}