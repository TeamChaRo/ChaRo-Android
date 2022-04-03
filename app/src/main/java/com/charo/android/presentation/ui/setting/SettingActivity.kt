package com.charo.android.presentation.ui.setting


import android.os.Bundle
import androidx.lifecycle.Observer
import com.charo.android.R
import com.charo.android.databinding.ActivitySettingBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.ui.setting.viewmodel.SettingViewModel
import com.charo.android.presentation.util.changeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingActivity : BaseActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    private val settingViewModel : SettingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickBackBtn()
        changeTabText()
        changeFragment(R.id.fragment_container_setting, SettingMainFragment())

    }

    private fun changeTabText(){
        settingViewModel.updateTabText.observe(this, Observer {
            binding.tvSettingUp.text = it
        })
    }

    //뒤로가기
    private fun clickBackBtn(){
        binding.btnSettingBack.setOnClickListener {
            finish()
        }

    }

}