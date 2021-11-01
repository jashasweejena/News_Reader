package com.example.newsreader.data.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Articles")
data class Article(
    @PrimaryKey
    var id: Long,
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
)