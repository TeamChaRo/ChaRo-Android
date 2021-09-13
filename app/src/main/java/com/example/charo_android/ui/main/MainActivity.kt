package com.example.charo_android.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityMainBinding
import com.example.charo_android.ui.charo.CharoFragment
import com.example.charo_android.ui.home.HomeFragment

import com.example.charo_android.ui.home.replaceFragment

import com.example.charo_android.ui.write.WriteFragment
import com.example.charo_android.ui.write.WriteShareActivity


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
                        startActivityWriteShare()
//                        replaceWriteFragment(userId,nickName)
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
                startActivityWriteShare()
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


    fun startActivityWriteShare() {
        val intent = Intent(this@MainActivity, WriteShareActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("nickname", nickName)
        startActivity(intent)
    }

}