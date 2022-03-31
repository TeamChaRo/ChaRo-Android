package com.charo.android.presentation.ui.charo.guestcharo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.charo.android.R
import com.charo.android.databinding.FragmentGuestCharoBinding
import com.charo.android.presentation.ui.charo.adapter.CharoFragmentStateAdapter
import com.charo.android.presentation.ui.signin.SocialSignInActivity
import com.google.android.material.tabs.TabLayoutMediator

class GuestCharoFragment : Fragment() {
    private var _binding: FragmentGuestCharoBinding? = null
    private val binding get() = _binding!!
    private val tabIconList = arrayListOf(
        R.drawable.ic_write_active,
        R.drawable.ic_save_5_active
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuestCharoBinding.inflate(layoutInflater, container, false)
        initializeViewPager()
        goLogin()
        return binding.root
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
                    GuestCharoChildFragment(), GuestCharoChildFragment()
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

    private fun goLogin() {
        binding.clMyCharoTitle.setOnClickListener {
            val intent = Intent(requireContext(), SocialSignInActivity::class.java)
            startActivity(intent)
        }
    }
}