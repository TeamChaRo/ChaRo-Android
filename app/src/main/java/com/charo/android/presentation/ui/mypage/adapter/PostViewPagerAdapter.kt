package com.charo.android.presentation.ui.mypage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.charo.android.presentation.ui.mypage.guest.GuestPostListFragment
import com.charo.android.presentation.ui.mypage.postlist.SavedPostFragment
import com.charo.android.presentation.ui.mypage.postlist.WrittenPostFragment

class PostViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    var fragmentList = listOf<String>()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(fragmentList[position]) {
            "WrittenPostFragment" -> WrittenPostFragment()
            "SavedPostFragment" -> SavedPostFragment()
            else -> GuestPostListFragment()
        }
    }
}