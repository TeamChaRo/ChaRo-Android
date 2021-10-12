package com.example.charo_android.presentation.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.changeFragment( layoutRes: Int, fragment: Fragment ) {

    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(layoutRes, fragment)
    .addToBackStack(null)
    .commit()


}