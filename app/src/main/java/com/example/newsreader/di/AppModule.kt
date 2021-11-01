package com.example.newsreader.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.newsreader.util.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    abstract fun bindContext(application: Application?): Context?

   companion object {
       @Singleton
       @Provides
       fun provideRetrofit(): Retrofit {
           return Retrofit.Builder()
               .baseUrl(Constants.NEWS_API_BASE_URL)
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .addConverterFactory(GsonConverterFactory.create())
               .build()
       }

       @Singleton
       @Provides
       fun provideConnectivityManager(application: Application): ConnectivityManager {
           return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
       }
   }
}