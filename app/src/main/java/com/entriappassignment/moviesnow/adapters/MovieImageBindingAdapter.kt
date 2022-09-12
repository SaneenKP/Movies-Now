package com.entriappassignment.moviesnow.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.entriappassignment.moviesnow.R
import com.entriappassignment.moviesnow.utils.Utils

@BindingAdapter("imageFromPath")
fun ImageView.imageFromImagePath(imagePath : String?){
    Glide.with(this.context)
        .load(imagePath?.let { Utils.getGlideUrl(it) })
        .placeholder(R.drawable.movie_image_placeholder)
        .into(this)
}
