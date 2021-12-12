package com.example.charo_android.presentation.ui.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.R
import com.example.charo_android.data.repository.local.setting.LocalSettingNoticeDataSource
import com.example.charo_android.data.repository.local.setting.LocalSettingNoticeDataSourceImpl
import com.example.charo_android.databinding.FragmentSettingNoticeBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.setting.adapter.SettingNoticeAdapter
import com.example.charo_android.presentation.ui.setting.viewmodel.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SettingNoticeFragment : BaseFragment<FragmentSettingNoticeBinding>(R.layout.fragment_setting_notice) {
    private val settingViewModel: SettingViewModel by sharedViewModel()
    private lateinit var settingNoticeAdapter: SettingNoticeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeTabText()
        initView()

    }

    private fun initView(){
        val settingNoticeData = LocalSettingNoticeDataSourceImpl().fetchData()
        Log.d("settingNotice", settingNoticeData.toString())
        settingNoticeAdapter = SettingNoticeAdapter()
        binding.rcSettingNotice.adapter = settingNoticeAdapter
        settingNoticeAdapter.setNoticeData(settingNoticeData)
    }


    //제목 변경
    private fun changeTabText() {
        settingViewModel.updateTabText.value = "공지사항"
    }
}