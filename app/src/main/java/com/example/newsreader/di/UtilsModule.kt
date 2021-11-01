package com.example.newsreader.di

import android.net.ConnectivityManager
import com.example.newsreader.util.IOnlineChecker
import com.example.newsreader.util.OnlineChecker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun bindOnlineChecker(connectivityManager: ConnectivityManager): IOnlineChecker {
        return OnlineChecker(connectivityManager)
    }
}