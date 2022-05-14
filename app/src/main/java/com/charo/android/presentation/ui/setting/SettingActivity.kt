package com.charo.android.presentation.ui.setting


import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.charo.android.R
import com.charo.android.databinding.ActivitySettingBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.ui.setting.viewmodel.SettingViewModel
import com.charo.android.presentation.util.changeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    private val settingViewModel: SettingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()

        changeFragment(R.id.fragment_container_setting, SettingMainFragment())
    }

    private fun initToolbar(){
        val toolbar = binding.toolbarSetting
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_1)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbarTitle.text = getString(R.string.setting)
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
}




