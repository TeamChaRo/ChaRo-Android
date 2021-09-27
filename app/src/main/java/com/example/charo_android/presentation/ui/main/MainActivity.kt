package com.example.charo_android.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.charo_android.R
import android.util.Log
import com.example.charo_android.databinding.ActivityMainBinding
import com.example.charo_android.presentation.ui.charo.CharoFragment
import com.example.charo_android.presentation.ui.home.HomeFragment
import com.example.charo_android.presentation.ui.home.replaceFragment
import com.example.charo_android.presentation.ui.write.WriteFragment
import com.example.charo_android.presentation.ui.write.WriteShareActivity


class MainActivity : AppCompatActivity() {
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val writeFragment : WriteFragment by lazy { WriteFragment() }
    private val charoFragment: CharoFragment by lazy { CharoFragment() }

    private lateinit var userEmail: String
    private lateinit var nickName: String
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userEmail = intent.getStringExtra("userId").toString()
        nickName = intent.getStringExtra("nickName").toString()
        replaceHomeFragment(userEmail, nickName)
        initNavView()
        Log.d("please", "제발 되라")
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

    fun getUserId(): String {
        return userEmail
    }

    fun getNickName(): String{
        return nickName
    }

    private fun initNavView() {
        binding.apply {

            navView.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.navigation_home -> {
                        replaceHomeFragment(userEmail,nickName)
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_write -> {
                        startActivityWriteShare()
//                        replaceWriteFragment(userId,nickName)
//                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_charo -> {
                        replaceCharoFragment(userEmail, nickName)
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

    private fun
            replaceCharoFragment(userId : String, nickName: String){
        replaceFragment(charoFragment,userId,nickName)
    }

    fun startActivityWriteShare() {
        val intent = Intent(this@MainActivity, WriteShareActivity::class.java)
        intent.putExtra("userId", userEmail)
        intent.putExtra("nickname", nickName)
        startActivity(intent)
    }

}