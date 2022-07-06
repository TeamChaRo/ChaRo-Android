package com.charo.android.presentation.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.charo.android.R
import com.charo.android.databinding.ActivitySettingReportBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.util.Define

class SettingReportActivity : BaseActivity<ActivitySettingReportBinding>(R.layout.activity_setting_report) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initGoogleForm()
    }

    private fun initGoogleForm(){
        binding.webReportGoogleForm.apply {
            WebViewClient()
            WebChromeClient()
            settings.javaScriptEnabled = true
            settings.useWideViewPort = true
            loadUrl(Define().reportForm)
        }
    }
}