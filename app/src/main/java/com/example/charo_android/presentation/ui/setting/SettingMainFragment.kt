package com.example.charo_android.presentation.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSettingMainBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.setting.viewmodel.SettingViewModel
import com.example.charo_android.presentation.ui.signin.SocialSignInActivity
import com.example.charo_android.presentation.ui.signup.SignUpPassWordFragment
import com.example.charo_android.presentation.util.SharedInformation
import com.example.charo_android.presentation.util.changeFragment
import com.kakao.sdk.user.UserApiClient
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SettingMainFragment : BaseFragment<FragmentSettingMainBinding>(R.layout.fragment_setting_main) {
    private val settingViewModel: SettingViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickProfileUpdate()
        changeTabText()
        clickLogOut()
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

    private fun changeTabText(){
        settingViewModel.updateTabText.value = "설정"
    }

    //로그아웃
    private fun clickLogOut(){
        val socialKeyStorage = SharedInformation
        val socialKey = socialKeyStorage.getSocialId(requireActivity())
        binding.textSettingLogout.setOnClickListener {
                if(socialKey == "1"){
                    UserApiClient.instance.unlink { error ->
                        if (error != null) {
                            Toast.makeText(requireActivity(), "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(requireActivity(), "로그아웃 성공", Toast.LENGTH_SHORT).show()
                        }
                        val intent = Intent(requireActivity(), SocialSignInActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        requireActivity().finish()
                    }
                }
        }

    }
}