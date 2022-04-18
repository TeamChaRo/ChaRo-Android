package com.charo.android.presentation.ui.follow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.charo.android.R
import com.charo.android.databinding.ActivityFollowBinding
import com.charo.android.presentation.ui.follow.adapter.FollowViewPagerAdapter
import com.charo.android.presentation.ui.follow.viewmodel.FollowViewModel
import com.charo.android.presentation.util.SharedInformation
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFollowBinding
    private val viewModel: FollowViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_follow)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setUserEmail(SharedInformation.getEmail(this))
        viewModel.setMyPageEmail(intent.getStringExtra("userEmail") ?: error(finish()))
        viewModel.setNickname(intent.getStringExtra("nickname") ?: error(finish()))

        initViewPager()
        getFollowList()
        clickBack()
    }

    override fun onRestart() {
        super.onRestart()
        getFollowList()
    }

    private fun initViewPager() {
        // ViewPager 초기화
        val followViewPagerAdapter = FollowViewPagerAdapter(this)
        followViewPagerAdapter.fragmentList = listOf(FollowerFragment(), FollowingFragment())
        binding.vp.adapter = followViewPagerAdapter

        // TabLayout 초기화 및 ViewPager와 연결
        TabLayoutMediator(binding.tab, binding.vp) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    private fun getFollowList() {
        viewModel.getFollowList()
    }

    private fun clickBack() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        val tabTextList = listOf("팔로워", "팔로잉")
    }
}