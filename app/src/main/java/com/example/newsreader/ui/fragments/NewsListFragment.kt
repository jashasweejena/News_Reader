package com.example.newsreader.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsreader.data.models.Article
import com.example.newsreader.databinding.FragmentNewsListBinding
import com.example.newsreader.ui.adapters.NewsListAdapter
import com.example.newsreader.ui.adapters.NewsListItemClickListener
import com.example.newsreader.ui.viewmodels.NewsViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class NewsListFragment @Inject constructor() : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var viewModel: NewsViewModel? = null

    private lateinit var binding: FragmentNewsListBinding

    private val subscription = CompositeDisposable()
    private val TAG = "NewsListFragment"

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)
            .get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        val adapter = NewsListAdapter(
            newsListItemClickListener = object: NewsListItemClickListener {
                override fun onNewsListItemClick(article: Article) {
                    findNavController().navigate(
                        NewsListFragmentDirections.actionNewsListFragmentToNewsDetailsFragment(article)
                    )
                }
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        viewModel?.let { viewModel ->
            subscription.add(
                viewModel.getNewsArticles()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        showProgressBar()
                    }
                    .subscribe (
                        { articles: List<Article> ->
                            hideProgressBar()
                            adapter.refreshData(articles.toMutableList())
                        }
                    )
                    {
                        hideProgressBar()
                        Log.d(TAG, "onCreate: " + it.message)
                        Toast.makeText(view.context, it.message, Toast.LENGTH_SHORT).show()
                    }
            )
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscription.clear()
    }


    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}