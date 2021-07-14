package com.example.charo_android.ui.home.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentMoreThemeViewBinding
import com.example.charo_android.databinding.FragmentMoreViewBinding
import com.example.charo_android.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator


class MoreThemeViewFragment : Fragment() {
    private var _binding: FragmentMoreThemeViewBinding? = null
    private val binding get() = _binding!!

    private val themeViewIcon = arrayListOf(
        R.drawable.ic_mouantin, R.drawable.ic_sea, R.drawable.ic_lake,
        R.drawable.ic_river, R.drawable.ic_spirng, R.drawable.ic_summer,
        R.drawable.ic_fall, R.drawable.ic_winter, R.drawable.ic_sea_road,
        R.drawable.ic_bloosom, R.drawable.ic_maple, R.drawable.ic_relax,
        R.drawable.ic_speed, R.drawable.ic_night_view, R.drawable.ic_city,
    )

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

        initMoreThemeViewPager()
        clickBackButton()
        return root
    }

    private fun initMoreThemeViewPager() {
        binding.apply {
            val moreThemeViewPagerAdapter = MoreThemeViewPagerAdapter(requireActivity())
            val moreThemeContentViewFragment = MoreThemeContentViewFragment()
            with(moreThemeViewPagerAdapter) {
                fragments = listOf(
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment(),
                    MoreThemeContentViewFragment()
                )
            }
            with(viewPagerMoreTheme) {
                adapter = moreThemeViewPagerAdapter
            }
            TabLayoutMediator(tabMoreThemeTab, viewPagerMoreTheme) { tab, position ->
                tab.setIcon(R.drawable.ic_winter_please_3x)
                tab.text = themeViewText[position]
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}