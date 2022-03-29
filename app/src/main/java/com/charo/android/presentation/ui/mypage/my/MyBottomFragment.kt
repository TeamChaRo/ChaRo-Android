package com.charo.android.presentation.ui.mypage.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentMyBottomBinding
import com.example.charo_android.presentation.ui.mypage.adapter.PostViewPagerAdapter
import com.example.charo_android.presentation.ui.mypage.postlist.SavedPostFragment
import com.example.charo_android.presentation.ui.mypage.postlist.WrittenPostFragment
import com.google.android.material.tabs.TabLayoutMediator

class MyBottomFragment : Fragment() {
    private var _binding: FragmentMyBottomBinding? = null
    val binding get() = _binding ?: error("binding not initiated")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_bottom, container, false)
        initViewPager()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViewPager() {
        // ViewPager 초기화
        val postViewPagerAdapter = PostViewPagerAdapter(requireActivity())
        postViewPagerAdapter.fragmentList = listOf(WrittenPostFragment(), SavedPostFragment())
        with(binding.vpProfile) {
            adapter = postViewPagerAdapter
            isUserInputEnabled = false
        }

        // TabLayout 초기화 및 ViewPager와 연결
        val tabIconList = arrayListOf(R.drawable.ic_write_active, R.drawable.ic_save_5_active)
        TabLayoutMediator(binding.tabProfile, binding.vpProfile) { tab, position ->
            tab.setIcon(tabIconList[position])
        }.attach()
    }
}