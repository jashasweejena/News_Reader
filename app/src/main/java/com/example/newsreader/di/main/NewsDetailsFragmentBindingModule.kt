package com.example.newsreader.di.main

import androidx.fragment.app.Fragment
import com.example.newsreader.di.annotations.FragmentKey
import com.example.newsreader.ui.fragments.NewsDetailsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class NewsDetailsFragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun newsDetailsFragment(): NewsDetailsFragment

    @Binds
    @IntoMap
    @FragmentKey(NewsDetailsFragment::class)
    abstract fun bindDummyFragment(fragment: NewsDetailsFragment): Fragment
}