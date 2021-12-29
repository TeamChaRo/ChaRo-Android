package com.example.charo_android.presentation.ui.charo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentCharoBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.ui.setting.SettingActivity
import com.google.android.material.tabs.TabLayoutMediator

class CharoFragment : Fragment() {
    private val charoViewModel: CharoViewModel by activityViewModels()
    private var _binding: FragmentCharoBinding? = null
    private val tabIconList = arrayListOf(
        R.drawable.ic_write_active,
        R.drawable.ic_save_5_active
    )
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charo, container, false)
        val root: View = binding.root
        goSettingView()
        goFollowView(Hidden.userId, Hidden.nickName)
        charoViewModel.getInitLikeData()
        charoViewModel.userInformation.observe(viewLifecycleOwner, {
            binding.myPageData = charoViewModel
        })

        initializeViewPager()

        return root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initializeViewPager() {
        binding.apply {
            val charoViewPagerAdapter = CharoFragmentStateAdapter(requireActivity())
            with(charoViewPagerAdapter) {
                fragmentList = listOf(
                    MyCharoFragment(), SaveFragment()
                )
            }
            with(binding.viewpagerCharo) {
                adapter = charoViewPagerAdapter
                isUserInputEnabled = false
            }

            TabLayoutMediator(binding.tabCharo, binding.viewpagerCharo) { tab, position ->
                tab.setIcon(tabIconList[position])
            }.attach()
        }
    }

    private fun goSettingView(){
        binding.imgCharoSetting.setOnClickListener {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goFollowView(myPageEmail: String, nickname: String) {
        binding.clCharoFollow.setOnClickListener {
            val intent = Intent(requireActivity(), CharoListActivity::class.java)
            intent.putExtra("myPageEmail", myPageEmail)
            intent.putExtra("nickname", nickname)
            startActivity(intent)
        }
    }
}