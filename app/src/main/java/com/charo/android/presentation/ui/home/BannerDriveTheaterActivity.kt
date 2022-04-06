package com.charo.android.presentation.ui.home

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.charo.android.R
import com.charo.android.databinding.ActivityBannerDriveTheaterBinding

class BannerDriveTheaterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBannerDriveTheaterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerDriveTheaterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        glideImg(resources.getDrawable(R.drawable.image_121), binding.ivPaju1)

    }

    private fun glideImg(img : Drawable, imageView : ImageView){
        Glide.with(this)
            .load(img)
            .transform(CenterCrop(), RoundedCorners(20))
            .into(imageView)
    }
}