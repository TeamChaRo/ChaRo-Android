package com.charo.android.presentation.ui.more.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter

class MoreThemeViewPagerAdapter(fm: FragmentActivity ) : FragmentStateAdapter(fm) {

    var fragments = listOf<Fragment>()
    val fragmentIds = fragments.map { it.hashCode().toLong() }

    override fun getItemCount(): Int = fragments.count()

    override fun createFragment(position: Int): Fragment{
        return fragments[position]
    }
    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragmentIds.contains(itemId)
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}