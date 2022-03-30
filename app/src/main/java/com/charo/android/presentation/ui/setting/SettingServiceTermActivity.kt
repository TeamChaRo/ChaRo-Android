package com.charo.android.presentation.ui.setting

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.base.BaseActivity
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivitySettingServiceTermBinding

class SettingServiceTermActivity : BaseActivity<ActivitySettingServiceTermBinding>(R.layout.activity_setting_service_term) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initServiceTerm()
        btnBack()
    }

    private fun initServiceTerm(){
        binding.webServiceTerm.apply {
            WebViewClient()
            WebChromeClient()
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            loadUrl(Hidden.serviceTerm)
        }
    }

    private fun btnBack(){
        binding.imgServiceTermBack.setOnClickListener {
            finish()
        }
    }
}