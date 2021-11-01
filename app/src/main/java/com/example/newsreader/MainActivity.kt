package com.example.newsreader

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.example.newsreader.data.models.Article
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.data.source.remote.ArticlesRemoteDataSource
import com.example.newsreader.data.source.remote.NewsApiService
import com.example.newsreader.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : DaggerBaseActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun executeBeforeOnCreate() {
        supportFragmentManager.fragmentFactory = fragmentFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(activityMainBinding.root)
    }
}