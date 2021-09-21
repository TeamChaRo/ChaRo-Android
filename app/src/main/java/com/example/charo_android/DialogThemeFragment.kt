package com.example.charo_android

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.charo_android.ui.DialogThemeViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DialogThemeFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = DialogThemeFragment()
    }
    // 1. activity_main.xml 에 존재하는 viewPager2 뷰 객체 초기화를 액티비티 lifecycle에 맞게 지연시킴
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_theme, container, false)

        // 2. 초기화 지연시킨 viewPager2 객체를 여기서 초기화함
        viewPager2 = bottomSheetView.findViewById(R.id.viewPager2)
        tabLayout = bottomSheetView.findViewById(R.id.tabLayout)

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
            tab.text = "Tab ${position+1}"
        }.attach()

//        tabLayout.setupWithViewPager(viewPager2)

//        val bottomSheetDialog = BottomSheetDialog(requireContext())
//        bottomSheetDialog.setContentView(bottomSheetView)

        showDialog(bottomSheetView)
        return bottomSheetView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun showDialog(bottomSheetView: View){
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//       // 3. viewPager2 뷰 객체에 어댑터 적용하기
//        val adapter = DialogThemeViewPagerAdapter(requireActivity())
//        adapter.addFragment(DialogThemeOneFragment())
//        adapter.addFragment(DialogThemeTwoFragment())
//        adapter.addFragment(DialogThemeThreeFragment())
//
//        viewPager2?.adapter = adapter
//        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int){
//                super.onPageSelected(position)
//                Log.e("ViewPagerFragment", "Page ${position+1}")
//            }
//        })
//
//        TabLayoutMediator(tabLayout, viewPager2){tab, position ->
//            tab.text = "Tab ${position+1}"
//        }.attach()
//    }


//    // 추가 기능 ) back 버튼 클릭시 화면 슬라이드 과거로 이동시키기
//    override fun onBackPressed() {
//        if(viewPager2.currentItem == 0) {
//            super.onBackPressed()
//        }else {
//            viewPager2.currentItem = viewPager2.currentItem - 1
//        }
//    }
}