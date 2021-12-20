package com.example.charo_android.presentation.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivitySettingServiceTermBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.base.BaseActivity

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