package com.reachmobi.scoresportify.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.reachmobi.scoresportify.R

@BindingAdapter("imageFromUrl")
fun ImageView.imageFromUrl(url: String?) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.ic_placeholder)
        .fallback(R.drawable.ic_no_image)
        .into(this)
}