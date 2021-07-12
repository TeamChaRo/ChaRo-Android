package com.example.charo_android.ui.home.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentMoreThemeViewBinding
import com.example.charo_android.databinding.FragmentMoreViewBinding
import com.example.charo_android.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator


class MoreThemeViewFragment : Fragment() {
    private var _binding: FragmentMoreThemeViewBinding? = null
    private val binding get() = _binding!!

    private val themeViewIcon = arrayListOf(
        R.drawable.image_home_theme, R.drawable.image_home_theme, R.drawable.image_home_theme,
        R.drawable.image_home_theme, R.drawable.image_home_theme, R.drawable.image_home_theme,
        R.drawable.image_home_theme, R.drawable.image_home_theme, R.drawable.image_home_theme,
        R.drawable.image_home_theme, R.drawable.image_home_theme, R.drawable.image_home_theme,
        R.drawable.image_home_theme, R.drawable.image_home_theme, R.drawable.image_home_theme,
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
        "#    도심"
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
            TabLayoutMediator(tabMoreTheme, viewPagerMoreTheme) { tab, position ->
                tab.setIcon(themeViewIcon[position])
                tab.text = themeViewText[position]
            }.attach()

        }
    }
    private fun clickBackButton() {
        binding.imgMoreThemeBackHome.setOnClickListener {
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