package com.charo.android.presentation.ui.mypage.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.charo.android.R
import com.charo.android.databinding.FragmentMyBottomBinding
import com.charo.android.presentation.ui.mypage.adapter.PostViewPagerAdapter
import com.charo.android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyBottomFragment : Fragment() {
    private var _binding: FragmentMyBottomBinding? = null
    val binding get() = _binding ?: error("binding not initiated")
    private val viewModel by sharedViewModel<MyPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_my_bottom, container, false)
        initViewPager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutSwipe.setColorSchemeResources(R.color.blue_main_0f6fff)
        setOnSwipeRefreshLayoutListener()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViewPager() {
        // ViewPager 초기화
        val postViewPagerAdapter = PostViewPagerAdapter(requireActivity())
        postViewPagerAdapter.fragmentList = listOf("WrittenPostFragment", "SavedPostFragment")
        with(binding.vpProfile) {
            adapter = postViewPagerAdapter
            isUserInputEnabled = false
        }

        // TabLayout 초기화 및 ViewPager와 연결
        val tabIconList = arrayListOf(R.drawable.selector_icon_write, R.drawable.selector_icon_save)
        TabLayoutMediator(binding.tabProfile, binding.vpProfile) { tab, position ->
            tab.setCustomView(R.layout.layout_custom_tab)
            tab.view.findViewById<ImageView>(R.id.icon).setBackgroundResource(tabIconList[position])
        }.attach()
    }

    private fun setOnSwipeRefreshLayoutListener() {
        binding.layoutSwipe.setOnRefreshListener {
            viewModel.getLikePost()
            viewModel.getNewPost()

            binding.layoutSwipe.isRefreshing = false
        }
    }
}