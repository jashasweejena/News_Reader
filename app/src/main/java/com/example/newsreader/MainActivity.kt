package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.data.models.Article
import com.example.newsreader.data.models.NewsResponse
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.data.source.remote.ArticlesRemoteDataSource
import com.example.newsreader.data.source.remote.NewsApiService
import com.example.newsreader.databinding.ActivityMainBinding
import com.example.newsreader.util.Constants
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var newsViewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(activityMainBinding.root)

        val retrofit =  Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.NEWS_API_BASE_URL)
            .build()

        val newsApi = retrofit.create(NewsApiService::class.java)
        val remoteDataSource = ArticlesRemoteDataSource(newsApi)

        val newsRepository = NewsRepository(remoteDataSource)

        newsViewModel = ViewModelProvider(this, NewsViewModelFactory(newsRepository)).get(NewsViewModel::class.java)

        val adapter = NewsListAdapter()
        activityMainBinding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        activityMainBinding.recyclerView.adapter = adapter

        newsViewModel.getNewsArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { articles: List<Article> ->
                    adapter.refreshData(articles as ArrayList<Article>)
                }
            )
            {
                Log.d(TAG, "onCreate: " + it.message)
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }

    }
}