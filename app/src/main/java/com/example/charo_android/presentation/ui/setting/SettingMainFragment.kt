package com.example.charo_android.presentation.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSettingMainBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.signup.SignUpPassWordFragment
import com.example.charo_android.presentation.util.changeFragment


class SettingMainFragment : BaseFragment<FragmentSettingMainBinding>(R.layout.fragment_setting_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickProfileUpdate()
        binding.switchAlarm.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {

                } else {

                }
            }
        })
    }


    private fun clickProfileUpdate() {
        binding.textSettingProfileUpdate.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.apply {
                replace(R.id.fragment_container_setting, SettingProfileUpdateFragment())
                addToBackStack(null)
                commit()
            }

        }
    }
}