package com.example.charo_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

import com.example.charo_android.ui.write.WriteActivity
import com.example.charo_android.ui.write.WriteFragment


class MainActivity : AppCompatActivity() {
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val writeFragment : WriteFragment by lazy { WriteFragment() }
    private val charoFragment: CharoFragment by lazy { CharoFragment() }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        replaceHomeFragment()
        initNavView()


    }


    private fun initNavView() {
        binding.apply {

            navView.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.navigation_home -> {
                        replaceHomeFragment()
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_write -> {
                        replaceWriteFragment()
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_charo -> {
                        replaceCharoFragment()
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }

            btnWrite.setOnClickListener {
                startActivityWrite()
            }
        }


    }

    private fun replaceHomeFragment(){
        replaceFragment(homeFragment)
    }
    private fun replaceWriteFragment(){
        replaceFragment(writeFragment)
    }

    private fun replaceCharoFragment(){
        replaceFragment(charoFragment)
    }


    fun startActivityWrite() {
        val intent = Intent(this@MainActivity, WriteActivity::class.java)
        startActivity(intent)
    }


}







