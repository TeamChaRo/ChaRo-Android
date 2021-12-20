package com.example.charo_android.presentation.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivitySettingPrivacyBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.base.BaseActivity

class SettingPrivacyActivity : BaseActivity<ActivitySettingPrivacyBinding>(R.layout.activity_setting_privacy) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPrivacy()
        btnBack()
    }


    private fun initPrivacy(){
        binding.webPrivacy.apply {
            WebViewClient()
            WebChromeClient()
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            loadUrl(Hidden.privacy)
        }
    }

    private fun btnBack(){
        binding.imgPrivacyBack.setOnClickListener {
            finish()
        }
    }
}