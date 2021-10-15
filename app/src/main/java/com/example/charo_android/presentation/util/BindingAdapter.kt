package com.example.charo_android.presentation.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imgBind")
    fun setImage(imageView: ImageView, imageUrl : String){
        Glide.with(imageView.context)
            .load(imageUrl)
            .transform(RoundedCorners(20))
            .into(imageView)

    }

    @JvmStatic
    @BindingAdapter("tagBind")
    fun setTag(textView: TextView, text: String){
        if (text == ""){
            textView.visibility = View.GONE
        } else{
            textView.text = text
        }
    }

}