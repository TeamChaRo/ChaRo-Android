package com.charo.android.presentation.ui.setting

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.charo.android.R
import com.charo.android.databinding.ActivitySettingPrivacyBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.util.Define


class SettingPrivacyActivity : BaseActivity<ActivitySettingPrivacyBinding>(R.layout.activity_setting_privacy) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPrivacy()
        initToolbar(getString(R.string.privacy))
    }

    private fun initToolbar(title : String){
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_1)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbarTitle.text = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initPrivacy(){
        binding.webPrivacy.apply {
            WebViewClient()
            WebChromeClient()
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            loadUrl(Define().privacy)
        }
    }
}