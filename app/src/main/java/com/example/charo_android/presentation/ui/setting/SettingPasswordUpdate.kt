package com.example.charo_android.presentation.ui.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSettingPassWordUpdateBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.setting.viewmodel.SettingViewModel
import com.example.charo_android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.regex.Pattern


class SettingPasswordUpdate :
    BaseFragment<FragmentSettingPassWordUpdateBinding>(R.layout.fragment_setting_pass_word_update) {
    private val settingViewModel: SettingViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        originPasswordConfirm()
        changeTabText()
    }


    //제목 변경
    private fun changeTabText() {
        settingViewModel.updateTabText.value = "비밀번호 수정"
    }


    private fun originPasswordConfirm() {
        val emailPattern = "^[a-zA-Z0-9]{5,15}$"
        val userEmail = SharedInformation.getEmail(requireActivity())
        with(binding) {
            etNewPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    settingViewModel.originPasswordCheck(userEmail, s.toString())
                    settingViewModel.passwordCheck.observe(viewLifecycleOwner){
                        if (s.toString().length < 5 || s.toString().length > 15) {
                            textInputPasswordUpdate.error = "5자 이상 15자 이내로 작성해 주세요."
                        } else if (!Pattern.matches(emailPattern, s.toString())) {
                            textInputPasswordUpdate.error = "영문과 숫자만 사용해 주세요."
                            Log.d("password", Pattern.matches(emailPattern, s.toString()).toString())
                        } else if (!it){
                            textInputPasswordUpdate.error = "다시 입력해주세요."
                        } else if(it){
                            textInputPasswordUpdate.error = null
                            textInputPasswordUpdate.isErrorEnabled = false
                            textInputPasswordUpdate.isHelperTextEnabled = true
                            textInputPasswordUpdate.helperText = "확인되었습니다."
                        }
                    }

                }
            })
        }
    }
}