package com.charo.android.presentation.ui.home

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.charo.android.R
import com.charo.android.databinding.ActivityBannerDriveTheaterBinding
import com.charo.android.presentation.ui.write.WriteShareActivity

class BannerDriveTheaterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityBannerDriveTheaterBinding

    private lateinit var requestManager : RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerDriveTheaterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()

        requestManager = Glide.with(this)
        glideImg(resources.getDrawable(R.drawable.image_121), binding.ivPaju1)

        binding.tvBtnBannerTheaterPajuPost.setOnClickListener(this)
        binding.tvBtnBannerTheaterGangPost.setOnClickListener(this)
    }

    private fun glideImg(img : Drawable, imageView : ImageView){
        requestManager.load(img)
            .transform(CenterCrop(), RoundedCorners(20))
            .into(imageView)
    }

    private fun initToolbar(){
        val toolbar = binding.toolbarBanner
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_1)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goDriveCourse(postId : Int){
        val intent = Intent(this, WriteShareActivity::class.java)
        intent.apply {
            putExtra("destination", "detail")
            putExtra("postId", postId)
        }
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.tvBtnBannerTheaterPajuPost -> goDriveCourse(41)
            binding.tvBtnBannerTheaterGangPost -> goDriveCourse(27)
        }
    }
}