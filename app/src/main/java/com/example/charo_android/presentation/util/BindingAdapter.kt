package com.example.charo_android.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imgBind")
    fun setImage(imageView: ImageView, imageUrl : String){
        Glide.with(imageView.context)
            .load(imageUrl)
            .transform(RoundedCorners(20))
            .into(imageView)
    }

}