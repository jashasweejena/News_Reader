package com.example.newsreader.di.main

import androidx.fragment.app.Fragment
import com.example.newsreader.ui.fragments.NewsListFragment
import com.example.newsreader.di.annotations.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class NewsListFragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun bindNewsListFragment(): NewsListFragment

    @Binds
    @IntoMap
    @FragmentKey(NewsListFragment::class)
    abstract fun bindDummyFragment(fragment: NewsListFragment): Fragment
}