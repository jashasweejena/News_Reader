package com.example.newsreader.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsreader.data.models.Article
import com.example.newsreader.databinding.ItemNewsBinding

class NewsListAdapter(
    private val articles: ArrayList<Article> = arrayListOf()
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.news = article
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount() = articles.size

    fun refreshData(articles: ArrayList<Article>) {
        this.articles.clear();
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }
}