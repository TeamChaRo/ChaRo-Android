package com.example.charo_android.presentation.ui.write

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.example.charo_android.databinding.DialogThemeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DialogThemeFragment : BottomSheetDialogFragment() {
    private var _binding: DialogThemeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogThemeBinding.inflate(layoutInflater, container, false)

        // 2. 초기화 지연시킨 viewPager2 객체를 여기서 초기화함
        viewPager2 = binding.viewPager2
        tabLayout = binding.tabLayout

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDialog()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun initDialog() {

        // 3. viewPager2 뷰 객체에 어댑터 적용하기
        val adapter = DialogThemeViewPagerAdapter(requireActivity())
        adapter.addFragment(DialogThemeOneFragment())
        adapter.addFragment(DialogThemeTwoFragment())
        adapter.addFragment(DialogThemeThreeFragment())

        viewPager2.adapter = adapter
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int){
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position+1}")
            }
        })

        TabLayoutMediator(tabLayout, viewPager2){tab, position ->
            tab.text = "테마 ${position+1}"
        }.attach()

        (dialog as BottomSheetDialog).behavior.apply {
            isFitToContents = false
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }

}