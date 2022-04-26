package com.charo.android.presentation.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.charo.android.databinding.ActivityOnBoardingBinding
import com.charo.android.presentation.ui.main.MainActivity
import com.charo.android.presentation.util.SharedInformation
import timber.log.Timber


class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //deeplink로 왔을 때 온보딩 건너뛰고 메인 -> 게시물
        if(intent != null && intent.getStringExtra("postId") != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        Timber.d("sharedOn ${SharedInformation.getSocialId(this)}")
        Timber.d("sharedOn ${SharedInformation.getEmail(this)}")

        val pagerAdapter = OnBoardingPagerAdapter(this)
        binding.vpOnboarding.adapter = pagerAdapter
        binding.dotsIndicator.setViewPager2(binding.vpOnboarding)
        SharedInformation.setOnBoarding(this)
    }

    private inner class OnBoardingPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa){
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
                return when(position){
                    0 -> OnBoardingOneFragment()
                    1 -> OnBoardingTwoFragment()
                    else -> OnBoardingThreeFragment()
                }
        }
    }
}