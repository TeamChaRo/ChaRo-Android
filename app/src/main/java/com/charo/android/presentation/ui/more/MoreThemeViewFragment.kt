package com.charo.android.presentation.ui.more

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnAttach
import com.charo.android.R
import com.charo.android.databinding.FragmentMoreThemeViewBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.main.SharedViewModel
import com.charo.android.presentation.ui.more.adapter.MoreThemeViewPagerAdapter
import com.charo.android.presentation.util.SharedInformation
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MoreThemeViewFragment :
    BaseFragment<FragmentMoreThemeViewBinding>(R.layout.fragment_more_theme_view) {
    private val sharedViewModel: SharedViewModel by sharedViewModel()
    private lateinit var moreThemeViewPagerAdapter: MoreThemeViewPagerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        sharedViewModel.moreFragment.value = true

        initMoreThemeViewPager()
        clickTab()
    }

    private fun initToolbar() {
        val toolbar = binding.toolbarMoreTheme
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_1)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                SharedInformation.removeThemeNum(requireActivity())
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initMoreThemeViewPager() {
        binding.apply {
            moreThemeViewPagerAdapter = MoreThemeViewPagerAdapter(requireActivity())
            viewPagerMoreTheme.adapter = moreThemeViewPagerAdapter
            val userId = SharedInformation.getEmail(requireActivity())
            with(moreThemeViewPagerAdapter) {
                fragments = listOf(
                    MoreThemeContentViewFragment(userId, "1", "spring"),
                    MoreThemeContentViewFragment(userId, "1", "summer"),
                    MoreThemeContentViewFragment(userId, "1", "fall"),
                    MoreThemeContentViewFragment(userId, "1", "winter"),
                    MoreThemeContentViewFragment(userId, "1", "mountain"),
                    MoreThemeContentViewFragment(userId, "1", "sea"),
                    MoreThemeContentViewFragment(userId, "1", "lake"),
                    MoreThemeContentViewFragment(userId, "1", "river"),
                    MoreThemeContentViewFragment(userId, "1", "oceanRoad"),
                    MoreThemeContentViewFragment(userId, "1", "blossom"),
                    MoreThemeContentViewFragment(userId, "1", "maple"),
                    MoreThemeContentViewFragment(userId, "1", "relax"),
                    MoreThemeContentViewFragment(userId, "1", "speed"),
                    MoreThemeContentViewFragment(userId, "1", "nightView"),
                    MoreThemeContentViewFragment(userId, "1", "cityView")
                )
            }
            sharedViewModel.themeNum.observe(viewLifecycleOwner) {
                var themeNum = it
                tabMoreThemeTab.getTabAt(themeNum)?.select()
                viewPagerMoreTheme.doOnAttach {
                    viewPagerMoreTheme.setCurrentItem(themeNum, false)
                }
            }



            TabLayoutMediator(tabMoreThemeTab, viewPagerMoreTheme) { tab, position ->
                when (position) {
                    0 -> tab.setCustomView(R.layout.tablayout_spring)
                    1 -> tab.setCustomView(R.layout.tablayout_summer)
                    2 -> tab.setCustomView(R.layout.tablayout_fall)
                    3 -> tab.setCustomView(R.layout.tablayout_winter)
                    4 -> tab.setCustomView(R.layout.tablayout_mountain)
                    5 -> tab.setCustomView(R.layout.tablayout_sea)
                    6 -> tab.setCustomView(R.layout.tablayout_lake)
                    7 -> tab.setCustomView(R.layout.tablayout_river)
                    8 -> tab.setCustomView(R.layout.tablayout_ocean_drive)
                    9 -> tab.setCustomView(R.layout.tablayout_blossom)
                    10 -> tab.setCustomView(R.layout.tablayout_maple)
                    11 -> tab.setCustomView(R.layout.tablayout_relax)
                    12 -> tab.setCustomView(R.layout.tablayout_speed)
                    13 -> tab.setCustomView(R.layout.tablayout_night_view)
                    14 -> tab.setCustomView(R.layout.tablayout_city)
                }
            }.attach()


        }
    }


    fun clickTab() {
        binding.tabMoreThemeTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {


                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }


}




