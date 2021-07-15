package com.example.charo_android.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.charo_android.R

fun AppCompatActivity.replaceFragment(fragment: Fragment, userId : String){
    val bundle = Bundle()
    bundle.putString("userId", userId)
    fragment.arguments = bundle
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.nav_host_fragment_activity_main, fragment)
        .addToBackStack(null)
        .commit()
}

