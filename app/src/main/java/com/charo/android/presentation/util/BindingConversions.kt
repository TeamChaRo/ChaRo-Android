package com.charo.android.presentation.util

import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

object BindingConversions {
    @BindingAdapter("profileImageUrl")
    @JvmStatic
    fun loadMyPageProfileImage(imageView: ImageView, url: String?) {
        if(url != null) {
            Glide.with(imageView.context)
                .load(url)
                .override(56 * DisplayMetrics().densityDpi, 56 * DisplayMetrics().densityDpi)
                .circleCrop()
                .into(imageView)
        }
    }

    @BindingAdapter("myPageRecyclerViewImageUrl")
    @JvmStatic
    fun loadMyPgaeRecyclerViewImage(imageView: ImageView, url: String?) {
        if(url != null) {
            Glide.with(imageView.context)
                .load(url)
                .transform(RoundedCorners(9))
                .into(imageView)
        }
    }

    @BindingAdapter("detailViewProfileImageUrl")
    @JvmStatic
    fun loadDetailProfileImage(imageView: ImageView, url: String?) {
        if(url != null) {
            Glide.with(imageView.context)
                .load(url)
                .override(29 * DisplayMetrics().densityDpi, 29*DisplayMetrics().densityDpi)
                .circleCrop()
                .into(imageView)
        }
    }

    @BindingAdapter("is_selected")
    @JvmStatic
    fun setSelected(view: View, selected: Boolean) {
        view.isSelected = selected
    }

    @BindingAdapter("is_INVISIBLE")
    @JvmStatic
    fun setInvisible(view: View, flag: Boolean) {
        if(flag) {
            view.visibility = View.INVISIBLE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    @BindingAdapter("is_GONE")
    @JvmStatic
    fun setGone(view: View, flag: Boolean) {
        if(flag) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }

    @BindingAdapter("show_province")
    @JvmStatic
    fun showProvince(textView: TextView, province: String?) {
        textView.text = province
    }

    @BindingAdapter("show_region")
    @JvmStatic
    fun showRegion(textView: TextView, region: String?) {
        if(region != null) {
            val province: String = textView.text.toString()
            if(province == "특별시" || province == "광역시") {
                textView.text = region.plus(province)
            } else {
                textView.text = province.plus(" ").plus(region)
            }
        }
    }
}