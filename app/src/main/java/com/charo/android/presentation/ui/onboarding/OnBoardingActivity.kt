package com.charo.android.presentation.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.charo.android.databinding.ActivityOnBoardingBinding
import com.charo.android.presentation.util.SharedInformation


class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("sharedOn", SharedInformation.getSocialId(this))
        Log.d("sharedOn", SharedInformation.getEmail(this))

        val pagerAdapter = OnBoardingPagerAdapter(this)
        binding.vpOnboarding.adapter = pagerAdapter
        binding.dotsIndicator.setViewPager2(binding.vpOnboarding)

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