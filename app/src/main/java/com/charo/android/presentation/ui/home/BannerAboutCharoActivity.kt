package com.charo.android.presentation.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.charo.android.R
import com.charo.android.databinding.ActivityBannerAboutCharoBinding
import com.charo.android.domain.model.home.BannerAboutCharo

class BannerAboutCharoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBannerAboutCharoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBannerAboutCharoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpBannerAboutCharo.adapter = BannerViewPagerAdapter(this)
        binding.vpBannerAboutCharo.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.dotsIndicator.setViewPager2(binding.vpBannerAboutCharo)

    }

    private inner class BannerViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa){
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> BannerAboutCharoFragment(BannerAboutCharo(R.drawable.banner_charo_look, "구경하고","사람들의 드라이브 경험을\n자유롭게 구경해보세요."))
                1 -> BannerAboutCharoFragment(BannerAboutCharo(R.drawable.banner_charo_search, "검색하고","원하는 지역과 테마로\n맞춤 드라이브 코스를 찾아보세요."))
                else -> BannerAboutCharoFragment(BannerAboutCharo(R.drawable.banner_charo_write, "작성하고","나만의 드라이브 코스를\n기록하고 공유해보세요."))
            }
        }
    }
}