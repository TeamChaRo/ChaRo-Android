package com.example.charo_android.ui.home.more

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.charo_android.R
import com.example.charo_android.api.ApiService
import com.example.charo_android.api.RequestThemeViewData
import com.example.charo_android.api.ResponseMoreViewData
import com.example.charo_android.databinding.FragmentMoreThemeViewBinding
import com.example.charo_android.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_charo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoreThemeViewFragment : Fragment() {
    private var _binding: FragmentMoreThemeViewBinding? = null
    private val binding get() = _binding!!
    var requestThemeData = mutableListOf<RequestThemeViewData>()
    private val themeViewIcon = arrayListOf(
        R.drawable.ic_mouantin, R.drawable.ic_sea, R.drawable.ic_lake,
        R.drawable.ic_river, R.drawable.ic_spirng, R.drawable.ic_summer,
        R.drawable.ic_fall, R.drawable.ic_winter, R.drawable.ic_sea_road,
        R.drawable.ic_bloosom, R.drawable.ic_maple, R.drawable.ic_relax,
        R.drawable.ic_speed, R.drawable.ic_night_view, R.drawable.ic_city,
    )
    private lateinit var userId : String

    private val themeViewText = arrayListOf(
        "#산",
        "#바다",
        "#호수",
        "#강",
        "#봄",
        "#여름",
        "#가을",
        "#겨울",
        "#해안도로",
        "#벚꽃",
        "#단풍",
        "#여유",
        "#스피드",
        "#야경",
        "#도심"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreThemeViewBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

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
                    MoreThemeContentViewFragment(userId,"1","mountain"),
                    MoreThemeContentViewFragment(userId,"1","sea"),
                    MoreThemeContentViewFragment(userId,"1","lake"),
                    MoreThemeContentViewFragment(userId,"1","river"),
                    MoreThemeContentViewFragment(userId,"1","spring"),
                    MoreThemeContentViewFragment(userId,"1","summer"),
                    MoreThemeContentViewFragment(userId,"1","fall"),
                    MoreThemeContentViewFragment(userId,"1","winter"),
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
                    0 -> tab.setCustomView(R.layout.tablayout_one)
                    1 -> tab.setCustomView(R.layout.tablayout_two)
                    2 -> tab.setCustomView(R.layout.tablayout_three)
                    3 -> tab.setCustomView(R.layout.tablayout_four)
                    4 -> tab.setCustomView(R.layout.tablayout_five)
                    5 -> tab.setCustomView(R.layout.tablayout_six)
                    6 -> tab.setCustomView(R.layout.tablayout_seven)
                    7 -> tab.setCustomView(R.layout.tablayout_eight)
                    8 -> tab.setCustomView(R.layout.tablayout_nine)
                    9 -> tab.setCustomView(R.layout.tablayout_ten)
                    10 -> tab.setCustomView(R.layout.tablayout_eleven)
                    11 -> tab.setCustomView(R.layout.tablayout_twelve)
                    12 -> tab.setCustomView(R.layout.tablayout_thirteen)
                    13 -> tab.setCustomView(R.layout.tablayout_fourteen)
                    14 -> tab.setCustomView(R.layout.tablayout_fifteen)

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

    private fun createTabView(){


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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

