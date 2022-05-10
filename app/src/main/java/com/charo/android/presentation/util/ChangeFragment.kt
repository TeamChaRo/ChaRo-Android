package com.charo.android.presentation.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

fun AppCompatActivity.changeFragment( layoutRes: Int, fragment: Fragment ) {
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(layoutRes, fragment)
    .commit()
}


