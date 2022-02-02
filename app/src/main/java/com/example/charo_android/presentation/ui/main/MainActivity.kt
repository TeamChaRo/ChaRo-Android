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
import com.example.charo_android.presentation.ui.charo.mypage.CharoFragment
import com.example.charo_android.presentation.ui.charo.otherpage.OtherCharoFragment
import com.example.charo_android.presentation.ui.home.HomeFragment
import com.example.charo_android.presentation.ui.mypage.MyPageFragment
import com.example.charo_android.presentation.util.replaceFragment
import com.example.charo_android.presentation.ui.write.WriteFragment
import com.example.charo_android.presentation.ui.write.WriteShareActivity
import com.example.charo_android.presentation.util.LoginUtil
import com.example.charo_android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val writeFragment: WriteFragment by lazy { WriteFragment() }
    private val charoFragment: CharoFragment by lazy { CharoFragment() }
    private val otherCharoFragment: OtherCharoFragment by lazy { OtherCharoFragment() }
    private val sharedViewModel : SharedViewModel by viewModel()
    private lateinit var userEmail: String
    private lateinit var nickName: String

    var otherUserEmail: String? = null
    var otherUserNickname: String? = null
    private var isMyPage: Boolean = true
    private var isFromOtherPage: Boolean = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var lookFor : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userEmail = SharedInformation.getEmail(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        requestPermissions()
        setContentView(binding.root)
        nickName = intent.getStringExtra("nickName").toString()
        otherUserEmail = intent.getStringExtra("otherUserEmail")
        otherUserNickname = intent.getStringExtra("otherUserNickname")
        isMyPage = intent.getBooleanExtra("isMyPage", true)
        isFromOtherPage = intent.getBooleanExtra("isFromOtherPage", false)
        Log.d("otherUserEmail", otherUserEmail.toString())
        Log.d("otherUserNickname", otherUserNickname.toString())
        Log.d("isMyPage", isMyPage.toString())
        Log.d("isFromOtherPage", isFromOtherPage.toString())
        when (isFromOtherPage) {
            true -> {
                binding.navView.selectedItemId = R.id.navigation_charo
                when (isMyPage) {
                    true -> replaceCharoFragment(userEmail, nickName, isMyPage)
                    false -> replaceCharoFragment(otherUserEmail!!, otherUserNickname!!, isMyPage)
                }
            }
            false -> replaceHomeFragment(userEmail, nickName)
        }
        initNavView()
        lookFor()


    }

    override fun onBackPressed() {
        finish()
    }

    private fun initNavView() {
        binding.apply {
            userEmail = SharedInformation.getEmail(this@MainActivity)
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
                        replaceCharoFragment(userEmail, nickName, isMyPage)
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



    private fun replaceWriteFragment(userId : String, nickName : String){
        if(userId == null || userEmail == "@"){

            //로그인 유도 필요한 곳에 작성
            LoginUtil.loginPrompt(this@MainActivity)
        } else {
            replaceFragment(writeFragment, userId, nickName)
        }
    }



    private fun replaceCharoFragment(userId: String, nickName: String, isMyPage: Boolean) {
        when (isMyPage) {
//            true -> replaceFragment(charoFragment, userId, nickName)
            // TODO: 테스트 코드입니다.
            true -> replaceFragment(MyPageFragment(), userId, nickName)
            false -> replaceFragment(OtherCharoFragment(), userId, nickName)
        }
    }


    private fun replaceCharoFragment(userId: String, nickName: String) {
        userEmail = SharedInformation.getEmail(this@MainActivity)
        if(userEmail == null || userEmail == "@"){
            LoginUtil.loginPrompt(this)
        }else{
            replaceFragment(charoFragment, userId, nickName)
        }

//        replaceFragment(otherCharoFragment, Hidden.otherUserEmail, Hidden.otherNickname)
    }

    fun startActivityWriteShare() {
        userEmail = SharedInformation.getEmail(this@MainActivity)
        Log.d("mainUserEmail", userEmail)
        if(userEmail == null || userEmail == "@"){
          //  로그인 유도 필요한 곳에 작성
            LoginUtil.loginPrompt(this)
        } else {
            val intent = Intent(this@MainActivity, WriteShareActivity::class.java)
            intent.putExtra("userId", userEmail)
            intent.putExtra("nickname", nickName)
            startActivity(intent)
        }
    }

    //둘러보기 이메일 받기
    private fun lookFor(){
        lookFor = intent.getStringExtra("lookForEmail").toString()
        sharedViewModel.lookForEmail.value = lookFor
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