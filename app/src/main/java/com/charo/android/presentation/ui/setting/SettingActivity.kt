package com.charo.android.presentation.ui.setting


import android.os.Bundle
import androidx.lifecycle.Observer
import com.charo.android.presentation.base.BaseActivity
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivitySettingBinding
import com.example.charo_android.presentation.ui.setting.viewmodel.SettingViewModel
import com.example.charo_android.presentation.util.changeFragment

class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    private val settingViewModel : SettingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeTabText()
        changeFragment(R.id.fragment_container_setting, SettingMainFragment())

    }

    private fun changeTabText(){
        settingViewModel.updateTabText.observe(this, Observer {
            binding.tvSettingUp.text = it
        })
    }


}