package com.charo.android.presentation.ui.setting

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.charo.android.R
import com.charo.android.databinding.ActivitySettingCsBinding
import com.charo.android.hidden.Hidden
import com.charo.android.presentation.base.BaseActivity


class SettingCsActivity : BaseActivity<ActivitySettingCsBinding>(R.layout.activity_setting_cs) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGoogleForm()

    }

    private fun initGoogleForm(){
        binding.webGoogleForm.apply {
            WebViewClient()
            WebChromeClient()
            settings.javaScriptEnabled = true
            settings.useWideViewPort = true
            loadUrl(Hidden.googleForm)
        }
    }
}