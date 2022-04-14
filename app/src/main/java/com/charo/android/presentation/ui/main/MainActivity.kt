package com.charo.android.presentation.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.charo.android.R
import com.charo.android.databinding.ActivityMainBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.ui.charo.mypage.CharoFragment
import com.charo.android.presentation.ui.charo.otherpage.OtherCharoFragment
import com.charo.android.presentation.ui.home.HomeFragment
import com.charo.android.presentation.ui.mypage.MyPageFragment
import com.charo.android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.charo.android.presentation.ui.write.WriteFragment
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.LoginUtil
import com.charo.android.presentation.util.SharedInformation
import com.charo.android.presentation.util.replaceFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var analytics: FirebaseAnalytics

    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val writeFragment: WriteFragment by lazy { WriteFragment() }
    private val charoFragment: CharoFragment by lazy { CharoFragment() }
    private val otherCharoFragment: OtherCharoFragment by lazy { OtherCharoFragment() }
    private val sharedViewModel : SharedViewModel by viewModel()
    private val myPageViewModel: MyPageViewModel by viewModel()
    private lateinit var userEmail: String
    private lateinit var nickName: String

    var otherUserEmail: String? = null
    var otherUserNickname: String? = null
    private var isMyPage: Boolean = true
    private var isFromOtherPage: Boolean = false
    private lateinit var lookFor : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtain the FirebaseAnalytics instance.
        analytics = Firebase.analytics

        userEmail = SharedInformation.getEmail(this)
        myPageViewModel.setUserEmail(userEmail)
        requestPermissions()
        nickName = intent.getStringExtra("nickName").toString()
        otherUserEmail = intent.getStringExtra("otherUserEmail")
        otherUserNickname = intent.getStringExtra("otherUserNickname")
        isMyPage = intent.getBooleanExtra("isMyPage", true)
        isFromOtherPage = intent.getBooleanExtra("isFromOtherPage", false)
        Timber.d("otherUserEmail ${otherUserEmail.toString()}")
        Timber.d("otherUserNickname ${otherUserNickname.toString()}")
        Timber.d("isMyPage $isMyPage")
        Timber.d("isFromOtherPage $isFromOtherPage")
        when (isFromOtherPage) {
            true -> {
                binding.navView.selectedItemId = R.id.navigation_charo
                when (isMyPage) {
                    true -> replaceCharoFragment(userEmail, nickName, isMyPage)
                    false -> replaceCharoFragment(otherUserEmail!!, otherUserNickname!!, isMyPage)
                }
            }
            false ->  replaceHomeFragment(userEmail, nickName)
        }
        initNavView()
        lookFor()

    }



   override fun onBackPressed() {
        finish()

    }

    private fun initNavView() {
        binding.navView.itemIconTintList = null
        binding.apply {
            userEmail = SharedInformation.getEmail(this@MainActivity)
            navView.setOnItemSelectedListener {
                val parameters : Bundle  = Bundle().apply {
                    this.putInt(FirebaseAnalytics.Param.ITEM_ID, it.itemId)
                    this.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "fragment")
                }

                when (it.itemId) {
                    R.id.navigation_home -> {
                        parameters.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.title_home_kor))
                        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, parameters)
                        replaceHomeFragment(userEmail, nickName)
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_write -> {
                        parameters.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.title_write_kor))
                        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, parameters)

                        startActivityWriteShare()
//                        replaceWriteFragment(userId,nickName)
//                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_charo -> {
                        parameters.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.title_charo_kor))
                        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, parameters)

                        replaceCharoFragment(userEmail, nickName, isMyPage)
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }

            binding.btnWrite.setOnClickListener {
                val parameters : Bundle  = Bundle().apply {
                    this.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(R.string.title_write_kor))
                    this.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "fragment")
                }
                analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, parameters)

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
        Timber.d("mainUserEmail", userEmail)
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