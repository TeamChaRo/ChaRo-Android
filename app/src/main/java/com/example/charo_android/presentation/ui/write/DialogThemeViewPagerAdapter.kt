package com.example.charo_android.presentation.ui.write

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DialogThemeViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    var fragments : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
    }

    fun removeFragment(){
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }

    fun showFragment(position: Int): Fragment {
        if(position == 0) {
            return DialogThemeOneFragment()
        }else if(position == 1) {
            return DialogThemeTwoFragment()
        }else {
            return DialogThemeThreeFragment()
        }
    }
}