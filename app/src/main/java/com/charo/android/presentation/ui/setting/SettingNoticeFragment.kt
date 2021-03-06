package com.charo.android.presentation.ui.setting

import android.os.Bundle
import android.view.View
import com.charo.android.R
import com.charo.android.data.datasource.local.setting.LocalSettingNoticeDataSourceImpl
import com.charo.android.databinding.FragmentSettingNoticeBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.setting.adapter.SettingNoticeAdapter
import timber.log.Timber


class SettingNoticeFragment :
    BaseFragment<FragmentSettingNoticeBinding>(R.layout.fragment_setting_notice) {
    private lateinit var settingNoticeAdapter: SettingNoticeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeTabText()
        initView()

    }

    private fun initView() {
        val settingNoticeData = LocalSettingNoticeDataSourceImpl().fetchData()
        Timber.d("settingNotice $settingNoticeData")
        settingNoticeAdapter = SettingNoticeAdapter()
        binding.rcSettingNotice.adapter = settingNoticeAdapter
        settingNoticeAdapter.setNoticeData(settingNoticeData)
    }

    //제목 변경
    private fun changeTabText() {
        (activity as SettingActivity).binding.toolbarTitle.text = "공지사항"
    }
}