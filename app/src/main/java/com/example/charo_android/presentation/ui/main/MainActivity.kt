package com.example.charo_android.presentation.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivityMainBinding
import com.example.charo_android.presentation.ui.charo.CharoFragment
import com.example.charo_android.presentation.ui.home.HomeFragment
import com.example.charo_android.presentation.ui.home.replaceFragment
import com.example.charo_android.presentation.ui.write.WriteFragment
import com.example.charo_android.presentation.ui.write.WriteShareActivity


class MainActivity : AppCompatActivity() {
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val writeFragment: WriteFragment by lazy { WriteFragment() }
    private val charoFragment: CharoFragment by lazy { CharoFragment() }


    private lateinit var userEmail: String
    private lateinit var nickName: String
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestPermissions()
        setContentView(binding.root)
        userEmail = intent.getStringExtra("userId").toString()
        nickName = intent.getStringExtra("nickName").toString()
        replaceHomeFragment(userEmail, nickName)
        initNavView()

        //권한 요청
        Log.d("lifecycler", "fir")
    }

    override fun onBackPressed() {
//        super.onBackPressed()
    }

    fun getUserId(): String {
        return userEmail
    }

    fun getNickName(): String {
        return nickName
    }

    private fun initNavView() {
        binding.apply {
            navView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        replaceHomeFragment(userEmail, nickName)
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

    private fun replaceHomeFragment(userId: String, nickName: String) {
        replaceFragment(homeFragment, userId, nickName)
    }

    private fun replaceWriteFragment(userId: String, nickName: String) {
        replaceFragment(writeFragment, userId, nickName)
    }


    private fun replaceCharoFragment(userId: String, nickName: String) {
        replaceFragment(charoFragment, userId, nickName)
    }

    fun startActivityWriteShare() {
        val intent = Intent(this@MainActivity, WriteShareActivity::class.java)
        intent.putExtra("userId", userEmail)
        intent.putExtra("nickname", nickName)
        startActivity(intent)
    }

    private fun requestPermissions() {
        val permissions: Array<String> =
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(this, "앱 이용을 위해 저장소 권한을 허용해야 합니다.", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(this, permissions, 1)
            } else {
                ActivityCompat.requestPermissions(this, permissions, 0)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            0 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 허용", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "앱 이용을 위해 권한 허용이 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            1 -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "권한 허용", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "앱 이용을 위해 권한 허용이 필요합니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
                startActivity(intent)
            }
        }
    }
}