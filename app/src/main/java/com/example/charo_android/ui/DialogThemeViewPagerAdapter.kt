package com.example.charo_android.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.charo_android.DialogThemeFragment
import com.example.charo_android.DialogThemeOneFragment
import com.example.charo_android.DialogThemeThreeFragment
import com.example.charo_android.DialogThemeTwoFragment

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