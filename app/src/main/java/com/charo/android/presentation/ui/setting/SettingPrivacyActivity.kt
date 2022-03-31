package com.charo.android.presentation.ui.setting

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.charo.android.R
import com.charo.android.databinding.ActivitySettingPrivacyBinding
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.base.BaseActivity


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