package com.example.charo_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.charo_android.databinding.ActivityMainBinding
import com.example.charo_android.ui.charo.CharoFragment
import com.example.charo_android.ui.home.HomeFragment

import com.example.charo_android.ui.home.HomeViewPagerAdapter
import com.example.charo_android.ui.home.replaceFragment
import com.example.charo_android.ui.search.SearchActivity

import com.example.charo_android.ui.write.WriteActivity
import com.example.charo_android.ui.write.WriteFragment


class MainActivity : AppCompatActivity() {
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val writeFragment : WriteFragment by lazy { WriteFragment() }
    private val charoFragment: CharoFragment by lazy { CharoFragment() }

    private lateinit var userId: String
    private lateinit var nickName: String
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId").toString()
        nickName = intent.getStringExtra("nickName").toString()
        replaceHomeFragment(userId, nickName)
        initNavView()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

    fun getUserId(): String {
        return userId
    }

    fun getNickName(): String{
        return nickName
    }

    private fun initNavView() {
        binding.apply {

            navView.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.navigation_home -> {
                        replaceHomeFragment(userId,nickName)
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_write -> {
                        startActivityWrite()
//                        replaceWriteFragment()
//                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_charo -> {
                        replaceCharoFragment(userId, nickName)
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }

            binding.btnWrite.setOnClickListener {
                startActivityWrite()
            }
        }


    }

    private fun replaceHomeFragment(userId : String, nickName : String){
        replaceFragment(homeFragment,userId, nickName)
    }

    private fun replaceWriteFragment(userId : String, nickName : String){
        replaceFragment(writeFragment,userId,nickName)
    }

    private fun replaceCharoFragment(userId : String, nickName: String){
        replaceFragment(charoFragment,userId,nickName)
    }


    fun startActivityWrite() {
        val intent = Intent(this@MainActivity, WriteActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("nickname", nickName)
        startActivity(intent)
    }
}