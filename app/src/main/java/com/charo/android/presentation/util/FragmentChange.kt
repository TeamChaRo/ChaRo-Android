package com.charo.android.presentation.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.charo_android.R

fun AppCompatActivity.replaceFragment(fragment: Fragment, userId : String, nickName: String){
    val bundle = Bundle()
    bundle.putString("userId", userId)
    bundle.putString("nickName", nickName)
    fragment.arguments = bundle
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.nav_host_fragment_activity_main, fragment)
        .addToBackStack(null)
        .commit()
}

