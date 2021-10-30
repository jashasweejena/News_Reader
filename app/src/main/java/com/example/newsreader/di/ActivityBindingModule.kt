package com.example.newsreader.di

import com.example.newsreader.MainActivity
import com.example.newsreader.di.scopes.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity?
}