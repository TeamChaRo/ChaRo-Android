package com.example.charo_android.presentation.ui.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.R
import com.example.charo_android.data.model.request.RequestThemeViewData
import com.example.charo_android.databinding.FragmentMoreThemeViewBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.home.HomeFragment
import com.example.charo_android.presentation.ui.more.adapter.MoreThemeViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MoreThemeViewFragment : BaseFragment<FragmentMoreThemeViewBinding>(R.layout.fragment_more_theme_view) {

    var requestThemeData = mutableListOf<RequestThemeViewData>()
    private lateinit var userId : String




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = arguments?.getString("userId").toString()

        initMoreThemeViewPager(userId)
        clickBackButton()
        clickTab()
    }



    private fun initMoreThemeViewPager(userId : String) {
        binding.apply {
            val moreThemeViewPagerAdapter = MoreThemeViewPagerAdapter(requireActivity())
            with(moreThemeViewPagerAdapter) {
                fragments = listOf(
                    MoreThemeContentViewFragment(userId,"1","spring"),
                    MoreThemeContentViewFragment(userId,"1","summer"),
                    MoreThemeContentViewFragment(userId,"1","fall"),
                    MoreThemeContentViewFragment(userId,"1","winter"),
                    MoreThemeContentViewFragment(userId,"1","mountain"),
                    MoreThemeContentViewFragment(userId,"1","sea"),
                    MoreThemeContentViewFragment(userId,"1","lake"),
                    MoreThemeContentViewFragment(userId,"1","river"),
                    MoreThemeContentViewFragment(userId,"1","oceanRoad"),
                    MoreThemeContentViewFragment(userId,"1","blossom"),
                    MoreThemeContentViewFragment(userId,"1","maple"),
                    MoreThemeContentViewFragment(userId,"1","relax"),
                    MoreThemeContentViewFragment(userId,"1","speed"),
                    MoreThemeContentViewFragment(userId,"1","nightView"),
                    MoreThemeContentViewFragment(userId,"1","cityView")
                )
            }
            with(viewPagerMoreTheme) {
                adapter = moreThemeViewPagerAdapter
            }

            TabLayoutMediator(tabMoreThemeTab, viewPagerMoreTheme) { tab, position ->
                when(position){
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
    private fun clickBackButton() {
        binding.imgBackTheme.setOnClickListener {
            val fragmentManager = activity?.supportFragmentManager
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment_activity_main, HomeFragment())
                ?.commit()
        }
    }


    fun clickTab(){
    binding.tabMoreThemeTab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            when(tab?.position) {


            }

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabReselected(tab: TabLayout.Tab?) {

        }
    })


    }


}

