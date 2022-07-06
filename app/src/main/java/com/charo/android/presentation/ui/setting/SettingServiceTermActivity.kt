package com.charo.android.presentation.ui.setting

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.charo.android.R
import com.charo.android.databinding.ActivitySettingServiceTermBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.util.Define


class SettingServiceTermActivity : BaseActivity<ActivitySettingServiceTermBinding>(R.layout.activity_setting_service_term) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar(getString(R.string.service))

        initServiceTerm()
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

    private fun initServiceTerm(){
        binding.webServiceTerm.apply {
            WebViewClient()
            WebChromeClient()
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            loadUrl(Define().serviceTerm)
        }
    }
}