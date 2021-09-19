package com.example.charo_android.presentation.ui.charo

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.R

object BindingConversions {
    @BindingAdapter("profileImageUrl")
    @JvmStatic
    fun loadProfileImage(imageView: ImageView, url: String?) {
        Log.d("loadProfileImage", "loadStart")
        if(url != null) {
            Glide.with(imageView.context)
                .load(url)
                .error(R.drawable.ellipse_4)
                .override(56, 56)
                .circleCrop()
                .into(imageView)
        }
    }

    @BindingAdapter("myPageRecyclerViewImageUrl")
    @JvmStatic
    fun loadMyPgaeRecyclerViewImage(imageView: ImageView, url: String?) {
        Log.d("loadMyPageRecyclerViewImage", "loadStart")
        if(url != null) {
            Glide.with(imageView.context)
                .load(url)
                .transform(RoundedCorners(9))
                .into(imageView)
        }
    }
}