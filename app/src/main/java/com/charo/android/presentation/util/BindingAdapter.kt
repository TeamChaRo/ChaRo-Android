package com.charo.android.presentation.util

import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.charo.android.R
import com.google.android.material.chip.Chip


object BindingAdapter {
    @JvmStatic
    @BindingAdapter("imgBind")
    fun setImage(imageView: ImageView, imageUrl: String) {
        val multiOption = MultiTransformation(CenterCrop(), RoundedCorners(14.dpToPx))
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.shape_r14_f3f8ff)
            .error(R.drawable.shape_r14_f3f8ff)
            .fallback(R.drawable.shape_r14_f3f8ff)
            .apply(RequestOptions.bitmapTransform(multiOption))
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("imgRoundBind10")
    fun setRadius10Image(imageView: ImageView, imageUrl: String) {
        val multiOption = MultiTransformation(CenterCrop(), RoundedCorners(10.dpToPx))
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.shape_r10_f3f8ff)
            .error(R.drawable.shape_r10_f3f8ff)
            .fallback(R.drawable.shape_r10_f3f8ff)
            .apply(RequestOptions.bitmapTransform(multiOption))
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("imgRoundBind")
    fun setRadius9Image(imageView: ImageView, imageUrl: String) {
        val multiOption = MultiTransformation(CenterCrop(), RoundedCorners(9.dpToPx))
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.shape_r9_f3f8ff)
            .error(R.drawable.shape_r9_f3f8ff)
            .fallback(R.drawable.shape_r9_f3f8ff)
            .apply(RequestOptions.bitmapTransform(multiOption))
            .into(imageView)
    }


    @JvmStatic
    @BindingAdapter("imgNoRoundBind")
    fun setNoRadiusImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("imgIntBind")
    fun setIntImage(imageView: ImageView, imageDrawable: Int) {
        Glide.with(imageView.context)
            .load(imageDrawable)
            .into(imageView)
    }

    // 확인요망
    @JvmStatic
    @BindingAdapter("profileBind")
    fun setProfileImage(imageView: ImageView, imageUri: Uri) {
        Glide.with(imageView.context)
            .load(imageUri)
            .transform(RoundedCorners(20.dpToPx))
            .centerCrop()
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("setCircleImageUrl")
    fun setCircleImageUrl(imageView: ImageView, imageUrl: String?) {
        if (TextUtils.isEmpty(imageUrl) || imageUrl == "null") {
            Glide.with(imageView.context)
                .load(R.drawable.ic_profile)
                .circleCrop()
                .into(imageView)
        } else {
            Glide.with(imageView.context)
                .load(imageUrl)
                .error(R.drawable.ic_profile)
                .circleCrop()
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("tagBind")
    fun setTag(textView: TextView, text: String) {
        if (text == "") {
            textView.visibility = View.GONE
        } else {
            textView.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("setCenterCropImageUrl")
    fun setCenterCropImageUrl(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)
    }

    // 확인요망
    @JvmStatic
    @BindingAdapter("chipBind")
    fun setChip(chip: Chip, text: String?) {
        if (text != null) {
            chip.text = text
        } else {
            chip.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("heartBind")
    fun setHeart(imageButton: ImageButton, favorite: Boolean) {
        var select = true
        imageButton.isSelected = favorite


    }

}