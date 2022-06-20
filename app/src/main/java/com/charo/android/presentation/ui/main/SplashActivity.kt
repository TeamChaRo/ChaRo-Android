package com.charo.android.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.charo.android.R
import com.charo.android.databinding.ActivitySplashBinding
import com.charo.android.presentation.ui.onboarding.OnBoardingActivity
import com.charo.android.presentation.ui.signin.SocialSignInActivity
import com.charo.android.presentation.util.Define
import com.charo.android.presentation.util.SharedInformation
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
    private val time: Long = 2000
    private var deepLinkPostId: Int = -1
    private var mContext: Context = this

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            val autoEmail = SharedInformation.getEmail(mContext)
            val onBoardingChecked = SharedInformation.getOnBoarding(mContext)
            Timber.d("autoEmail $autoEmail")
            Timber.d("onBoardingChecked $onBoardingChecked")

            handleDeepLink()
            delay(time)
            autoLogin(autoEmail, onBoardingChecked)
        }
    }

    private fun autoLogin(autoEmail: String, onBoardingChecked: Boolean){
        val intent : Intent = if (autoEmail != "@") {
            Timber.d("autoEmail $autoEmail")
            Toast.makeText(this, "자동 로그인 성공", Toast.LENGTH_SHORT).show()
            Intent(this, MainActivity::class.java)
        } else {
            if(onBoardingChecked) {
                Intent(this, OnBoardingActivity::class.java)
            } else {
                Intent(this, SocialSignInActivity::class.java)
            }
        }
        intent.putExtra("postId", deepLinkPostId)
        Timber.d("[DynamicLink] autoLogin $deepLinkPostId")

        startActivity(intent)
        finish()
    }

    private suspend fun handleDeepLink() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    Timber.d("[DynamicLink] deepLink $deepLink")

                    if (deepLink != null) {
                        val segment = deepLink.lastPathSegment
                        Timber.d("[DynamicLink] segment $segment")

                        if (segment == Define().DTL_SEGMENT) {
                            val strPostId = deepLink.getQueryParameter("postId")
                            deepLinkPostId = strPostId?.toInt() ?: -1
                            Timber.d("[DynamicLink] deepLinkPostId $deepLinkPostId")
                        }
                    }
                }
            }
            .addOnFailureListener(this) { e ->
                run {
                    Timber.w("[DynamicLink] getDynamicLink:onFailure $e")
                }
            }
    }
}