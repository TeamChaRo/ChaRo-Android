package com.example.charo_android.presentation.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivitySettingCsBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.base.BaseActivity

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