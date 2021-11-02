package com.example.newsreader.ui.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.newsreader.R

object ImageViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:setImgSrc")
    fun setImageSrc(iv: ImageView, imageUri: String?) {
        Glide.with(iv.context)
            .load(imageUri)
            .placeholder(R.drawable.placeholder)
            .into(iv)
    }
}