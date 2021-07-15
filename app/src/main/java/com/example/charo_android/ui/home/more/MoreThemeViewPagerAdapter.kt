package com.example.charo_android.ui.home.more

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.charo_android.api.ResponseMoreViewData
import com.example.charo_android.databinding.ItemMoreThemeContentViewBinding

class MoreThemeViewPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
    var fragments = listOf<Fragment>()

    override fun getItemCount(): Int = fragments.count()

    override fun createFragment(position: Int): Fragment = fragments[position]

}