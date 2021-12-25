package com.example.charo_android.presentation.ui.charo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentCharoListBinding
import com.example.charo_android.hidden.Hidden
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.dialog_theme.*

class CharoListFragment(private val userEmail: String, private val userNickname: String) : Fragment() {
    private var _binding: FragmentCharoListBinding? = null
    private val binding get() = _binding!!
    private val charoViewModel: CharoViewModel by activityViewModels()
    private val tabTextList = arrayListOf("팔로워", "팔로잉")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charo_list, container, false)
        setTopTitle()
        initViewPager()
        getFollowData()
        goMyPage()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setTopTitle() {
        binding.tvCharoListTitle.text = userNickname
    }

    private fun initViewPager() {
        binding.apply {
            val charoListViewPagerAdapter = CharoListViewPagerAdapter(requireActivity())
            charoListViewPagerAdapter.fragmentList = listOf(CharoListFollowerFragment(), CharoListFollowingFragment())
            viewPagerCharoList.adapter = charoListViewPagerAdapter

            TabLayoutMediator(tabLayoutCharoList, viewPagerCharoList) { tab, position ->
                tab.text = tabTextList[position]
            }.attach()
        }
    }

    private fun getFollowData() {
        val myPageEmail: String? = (activity as CharoListActivity).myPageEmail
        Log.d("myPageEmail", myPageEmail.toString())
        myPageEmail?.let { charoViewModel.getFollowData(Hidden.userId, myPageEmail) }
    }

    private fun goMyPage() {
        binding.imgCharoListBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}