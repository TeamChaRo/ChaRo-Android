package com.example.charo_android.presentation.ui.setting

import android.os.Bundle
import android.view.View
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSettingCsBinding
import com.example.charo_android.hidden.Hidden
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.setting.viewmodel.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SettingCsFragment : BaseFragment<FragmentSettingCsBinding>(R.layout.fragment_setting_cs) {
    private val settingViewModel: SettingViewModel by sharedViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGoogleForm()
        changeTabText()
    }


    private fun initGoogleForm(){
        binding.webGoogleForm.loadUrl(Hidden.googleForm)
    }

    private fun changeTabText() {
        settingViewModel.updateTabText.value = "1:1 문의"
    }
}