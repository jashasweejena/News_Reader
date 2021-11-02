package com.example.newsreader.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsreader.data.Resource
import com.example.newsreader.data.models.Article
import com.example.newsreader.data.source.NewsRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsViewModel
@Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {
    private var _newsLiveData: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    var newsLiveData: LiveData<Resource<List<Article>>> = _newsLiveData

    private val compositeDisposable = CompositeDisposable()

    fun getNewsArticles()  {
        compositeDisposable.add(
            newsRepository.getArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _newsLiveData.postValue(Resource.Loading(null))
                }
                .doOnError {
                    _newsLiveData.postValue(Resource.Error(it.message ?: "Something went wrong"))
                }
                .subscribe { articles: List<Article> ->
                    _newsLiveData.postValue(Resource.Success(articles))
                }
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}