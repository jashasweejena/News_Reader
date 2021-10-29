package com.example.newsreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.data.models.NewsResponse
import com.example.newsreader.data.source.NewsApiService
import com.example.newsreader.databinding.ActivityMainBinding
import com.example.newsreader.databinding.ItemNewsBinding
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

        val singleNewsReponse = newsApi.getNews("in", Constants.API_KEY)

        val adapter = NewsListAdapter()
        activityMainBinding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        activityMainBinding.recyclerView.adapter = adapter


        singleNewsReponse.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: SingleObserver<NewsResponse> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: NewsResponse) {
                    adapter.refreshData(t.articles)
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onError: " + e.message)
                }
            })

    }
}