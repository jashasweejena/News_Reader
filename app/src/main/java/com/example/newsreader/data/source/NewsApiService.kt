package com.example.newsreader.data.source

import com.example.newsreader.data.models.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    fun getNews(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String?
    ): Single<NewsResponse>
}