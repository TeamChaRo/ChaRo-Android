package com.charo.android.presentation.ui.mypage.guest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentGuestBottomBinding
import com.example.charo_android.presentation.ui.mypage.adapter.PostViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class GuestBottomFragment : Fragment() {
    private var _binding: FragmentGuestBottomBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_guest_bottom,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViewPager() {
        // ViewPager 초기화
        val postViewPagerAdapter = PostViewPagerAdapter(requireActivity())
        postViewPagerAdapter.fragmentList = listOf(GuestPostListFragment(), GuestPostListFragment())
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