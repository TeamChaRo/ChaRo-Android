package com.charo.android.presentation.ui.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.charo.android.R
import com.charo.android.databinding.ActivityBannerAboutCharoBinding
import com.charo.android.domain.model.home.BannerAboutCharo
import com.charo.android.presentation.util.Define

class BannerAboutCharoActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityBannerAboutCharoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerAboutCharoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()

        binding.vpBannerAboutCharo.adapter = BannerViewPagerAdapter(this)
        binding.vpBannerAboutCharo.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.dotsIndicator.setViewPager2(binding.vpBannerAboutCharo)

        binding.ivInsta.setOnClickListener(this)
        binding.tvInstaId.setOnClickListener(this)
        binding.tvInstaContext.setOnClickListener(this)

    }

    private fun initToolbar(){
        val toolbar = binding.toolbarBanner
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_1)
    }

    private fun openInsta(){
        val InstagramPageID = Define().INSTA_CHARO_ID
        val uri = Uri.parse("http://instagram.com/_u/$InstagramPageID")

        val Instagram_Intent = Intent(Intent.ACTION_VIEW, uri)
        Instagram_Intent.setPackage("com.instagram.android")

        kotlin.runCatching {
            startActivity(Instagram_Intent)
        }.onFailure{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/$InstagramPageID")))
        }
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.ivInsta, binding.tvInstaId, binding.tvInstaContext -> {
                openInsta()
            }
        }
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

    private inner class BannerViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa){
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> BannerAboutCharoFragment(BannerAboutCharo(R.drawable.banner_charo_look, "????????????","???????????? ???????????? ?????????\n???????????? ??????????????????."))
                1 -> BannerAboutCharoFragment(BannerAboutCharo(R.drawable.banner_charo_search, "????????????","????????? ????????? ?????????\n?????? ???????????? ????????? ???????????????."))
                else -> BannerAboutCharoFragment(BannerAboutCharo(R.drawable.banner_charo_write, "????????????","????????? ???????????? ?????????\n???????????? ??????????????????."))
            }
        }
    }
}
