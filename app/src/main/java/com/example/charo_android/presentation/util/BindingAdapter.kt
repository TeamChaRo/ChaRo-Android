package com.example.charo_android.presentation.util

import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.charo_android.R
import com.google.android.material.chip.Chip

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imgBind")
    fun setImage(imageView: ImageView, imageUrl : String){
        Glide.with(imageView.context)
            .load(imageUrl)
            .transform(RoundedCorners(20.dpToPx))
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("imgRoundBind")
    fun setRadius9Image(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.rectangle_radius_5)
            .transform(RoundedCorners(9.dpToPx))
            .into(imageView)
    }


    @JvmStatic
    @BindingAdapter("imgNoRoundBind")
    fun setNoRadiusImage(imageView: ImageView, imageUrl : String){
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("imgIntBind")
    fun setIntImage(imageView: ImageView, imageDrawable : Int){
        Glide.with(imageView.context)
            .load(imageDrawable)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("profileBind")
    fun setProfileImage(imageView: ImageView, imageUri : Uri){
        Glide.with(imageView.context)
            .load(imageUri)
            .transform(RoundedCorners(20.dpToPx))
            .centerCrop()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("setCircleImageUrl")
    fun setCircleImageUrl(imageView: ImageView, imageUrl: String?) {
        if(imageUrl != null) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .circleCrop()
                .into(imageView)
        }
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

    @JvmStatic
    @BindingAdapter("chipBind")
    fun setChip(chip: Chip, text: String?){
        if (text != null){
            chip.text = text
        } else{
            chip.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("heartBind")
    fun setHeart(imageButton: ImageButton, favorite : Boolean){
        var select = true
        imageButton.isSelected = favorite




    }

}