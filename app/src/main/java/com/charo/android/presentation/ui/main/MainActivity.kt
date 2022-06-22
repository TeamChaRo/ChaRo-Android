package com.charo.android.presentation.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.charo.android.R
import com.charo.android.databinding.ActivityMainBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.ui.home.HomeFragment
import com.charo.android.presentation.ui.mypage.MyPageFragment
import com.charo.android.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.charo.android.presentation.ui.write.WriteFragment
import com.charo.android.presentation.ui.write.WriteShareActivity
import com.charo.android.presentation.util.LoginUtil
import com.charo.android.presentation.util.SharedInformation
import com.charo.android.presentation.util.changeFragment
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
    private val sharedViewModel: SharedViewModel by viewModel()
    private val myPageViewModel: MyPageViewModel by viewModel()
    private lateinit var userEmail: String
    private lateinit var nickName: String

    var otherUserEmail: String? = null
    var otherUserNickname: String? = null
    private var isMyPage: Boolean = true
    private var isFromOtherPage: Boolean = false
    private lateinit var lookFor: String
    private var lastTimeBackPressed: Long = 0

    val myPageResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    result.data?.let {
                        myPageViewModel.postId = it.getIntExtra("postId", 0)
                        myPageViewModel.likesCount = it.getIntExtra("likesCount", 0)
                        myPageViewModel.saveCountDiff = it.getIntExtra("saveCountDiff", 0)

                        if (myPageViewModel.postId > 0) {
                            myPageViewModel.updatePost()
                        }
                    } ?: Timber.tag("slog").i("result.data is null")
                }
                RESULT_CANCELED -> {
                    Timber.tag("slog resultCode").i("RESULT_CANCELED")
                }
                else -> {
                    Timber.tag("slog resultCode").i(result.resultCode.toString())
                }
            }
        }

    val followResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {
                    myPageViewModel.follower = it.getIntExtra("follower", -1)
                    myPageViewModel.following = it.getIntExtra("following", -1)

                    if (myPageViewModel.follower != -1 && myPageViewModel.following != -1) {
                        myPageViewModel.updateFollow()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deepLinkDetailPost()

        // Obtain the FirebaseAnalytics instance.
        analytics = Firebase.analytics

        userEmail = SharedInformation.getEmail(this)
        myPageViewModel.setUserEmail(userEmail)

        if (!SharedInformation.notRequestAllow) {
            requestPermissions()
        }

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
            false -> replaceHomeFragment(userEmail, nickName)
        }
        initNavView()
        lookFor()

    }

    override fun onResume() {
        super.onResume()
        if (SharedInformation.searchWrite) {
            binding.navView.selectedItemId = R.id.navigation_write
            SharedInformation.searchWrite = false
        }
    }

    override fun onBackPressed() {
        if (sharedViewModel.moreFragment.value == true) {
            changeFragment(R.id.nav_host_fragment_activity_main, homeFragment)
            Timber.d("moreview에서 onBackPressed호출됨")
            sharedViewModel.moreFragment.value = false
        } else {
            Timber.d("mainactiviy에서 onBackPressed호출됨 : finish")
            if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
                finish()
                return
            }
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this, "한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deepLinkDetailPost() {
        Timber.d(
            "MainActivity deepLinkDetailPost, intent : $intent, postId : ${
                intent.getStringExtra(
                    "postId"
                )
            }"
        )

        val postId = intent.getIntExtra("postId", -1)
        if (intent != null && postId != -1) {
            val intent = Intent(this, WriteShareActivity::class.java)
            intent.putExtra("postId", postId)
            intent.putExtra("destination", "detail")
            startActivity(intent)
        }
    }

    private fun initNavView() {
        binding.navView.itemIconTintList = null
        binding.apply {
            userEmail = SharedInformation.getEmail(this@MainActivity)
            navView.setOnItemSelectedListener {
                val parameters: Bundle = Bundle().apply {
                    this.putInt(FirebaseAnalytics.Param.ITEM_ID, it.itemId)
                    this.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "fragment")
                }

                when (it.itemId) {
                    R.id.navigation_home -> {
                        parameters.putString(
                            FirebaseAnalytics.Param.ITEM_NAME,
                            getString(R.string.title_home_kor)
                        )
                        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, parameters)
                        replaceHomeFragment(userEmail, nickName)
                        return@setOnItemSelectedListener true
                    }
                    R.id.navigation_write -> {
                        parameters.putString(
                            FirebaseAnalytics.Param.ITEM_NAME,
                            getString(R.string.title_write_kor)
                        )
                        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, parameters)

                        startActivityWriteShare()
                    }
                    R.id.navigation_charo -> {
                        parameters.putString(
                            FirebaseAnalytics.Param.ITEM_NAME,
                            getString(R.string.title_charo_kor)
                        )
                        analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, parameters)

                        replaceCharoFragment(userEmail, nickName, isMyPage)
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }

            binding.btnWrite.setOnClickListener {
                val parameters: Bundle = Bundle().apply {
                    this.putString(
                        FirebaseAnalytics.Param.ITEM_NAME,
                        getString(R.string.title_write_kor)
                    )
                    this.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "fragment")
                }
                analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, parameters)

                startActivityWriteShare()
            }
        }
    }

    private fun replaceHomeFragment(userId: String, nickName: String) {
        SharedInformation.notRequestAllow = false
        replaceFragment(homeFragment, userId, nickName)
    }


    private fun replaceWriteFragment(userId: String, nickName: String) {
        SharedInformation.notRequestAllow = false
        if (userId == null || userEmail == "@") {

            //로그인 유도 필요한 곳에 작성
            LoginUtil.loginPrompt(this@MainActivity)
        } else {
            replaceFragment(writeFragment, userId, nickName)
        }
    }


    private fun replaceCharoFragment(userId: String, nickName: String, isMyPage: Boolean) {
        SharedInformation.notRequestAllow = true
        when (isMyPage) {
            true -> replaceFragment(MyPageFragment(), userId, nickName)
            false -> throw IllegalArgumentException()
        }
    }


    fun startActivityWriteShare() {
        userEmail = SharedInformation.getEmail(this@MainActivity)
        Timber.d("mainUserEmail $userEmail")
        if (userEmail == null || userEmail == "@") {
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
    private fun lookFor() {
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
                    SharedInformation.setPermissionNever(this, false)
                    Toast.makeText(
                        this,
                        getString(R.string.txt_allow_permission),
                        Toast.LENGTH_SHORT
                    ).show()
                } else { //다시묻지않기
                    SharedInformation.setPermissionNever(this, true)
                }
            }
            1 -> { //거부
                SharedInformation.setPermissionNever(this, false)
                Toast.makeText(this, getString(R.string.txt_need_permission), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}