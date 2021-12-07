package com.example.charo_android.presentation.ui.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSettingPassWordUpdateBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.setting.viewmodel.SettingViewModel
import com.example.charo_android.presentation.util.CustomToast
import com.example.charo_android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.regex.Pattern


class SettingPasswordUpdate :
    BaseFragment<FragmentSettingPassWordUpdateBinding>(R.layout.fragment_setting_pass_word_update) {
    private val settingViewModel: SettingViewModel by sharedViewModel()
    val emailPattern = "^[a-zA-Z0-9]{5,15}$"



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newPasswordRegister()
        originPasswordConfirm()
        changeTabText()
        inVisibleNewPassword()
        newPasswordReconfirm()
    }


    //제목 변경
    private fun changeTabText() {
        settingViewModel.updateTabText.value = "비밀번호 수정"
    }

    // 새비밀번호 수정 숨기기
    private fun inVisibleNewPassword(){
        binding.clNewPasswordUpdate.visibility = View.INVISIBLE
    }

    private fun originPasswordConfirm() {
        val userEmail = SharedInformation.getEmail(requireActivity())
        with(binding) {
            etPasswordUpdate.addTextChangedListener(object : TextWatcher {
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
                    if (s.toString().length < 5 || s.toString().length > 15) {
                        textInputPasswordUpdate.error = "5자 이상 15자 이내로 작성해 주세요."
                    } else if (!Pattern.matches(emailPattern, s.toString())) {
                        textInputPasswordUpdate.error = "영문과 숫자만 사용해 주세요."
                        Log.d("password", Pattern.matches(emailPattern, s.toString()).toString())
                    } else {
                        settingViewModel.originPasswordCheck(userEmail, s.toString())
                        settingViewModel.passwordCheck.observe(viewLifecycleOwner) {
                            Log.d("passwordCheck", it.toString())
                            if (it == false) {
                                textInputPasswordUpdate.error = "다시 입력해주세요."
                            } else {
                                textInputPasswordUpdate.helperText = "확인되었습니다."
                                clNewPasswordUpdate.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            })
        }
    }

    // 새 비밀번호 설정
    private fun newPasswordRegister(){

        with(binding){
            etNewPassword.addTextChangedListener(object: TextWatcher{
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
                    if (s.toString().length < 5 || s.toString().length > 15) {
                        textInputNewPassword.error = "5자 이상 15자 이내로 작성해 주세요."
                    } else if (!Pattern.matches(emailPattern, s.toString())) {
                        textInputNewPassword.error = "영문과 숫자만 사용해 주세요."
                        Log.d("password", Pattern.matches(emailPattern, s.toString()).toString())
                    }else{
                        textInputNewPassword.helperText = "확인되었습니다."
                        settingViewModel.newPasswordReconfirm.value = s.toString()
                    }
                }
            })
        }

    }

    private fun newPasswordReconfirm(){
        val userEmail = SharedInformation.getEmail(requireActivity())
        with(binding){
            etNewPasswordReconfirm.addTextChangedListener(object: TextWatcher{
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
                    if (s.toString().length < 5 || s.toString().length > 15) {
                        textInputNewPasswordReconfirm.error = "5자 이상 15자 이내로 작성해 주세요."
                    } else if (!Pattern.matches(emailPattern, s.toString())) {
                        textInputNewPasswordReconfirm.error = "영문과 숫자만 사용해 주세요."
                        Log.d("password", Pattern.matches(emailPattern, s.toString()).toString())
                    }else{
                        settingViewModel.newPasswordReconfirm.observe(viewLifecycleOwner){
                            if(it == s.toString()){
                                textInputNewPasswordReconfirm.helperText = "사용가능한 비밀번호입니다."
                                imgPasswordUpdateButton.setImageResource(R.drawable.sign_up_next)
                            }
                        }
                        binding.imgPasswordUpdateButton.setOnClickListener {
                            settingViewModel.newPasswordRegister(userEmail, s.toString())
                            CustomToast.createPasswordUpdateToast(requireActivity(),"비밀번호가 변경되었습니다.")?.show()
                            val transaction = activity?.supportFragmentManager?.beginTransaction()
                            transaction?.apply {
                                replace(R.id.fragment_container_setting, SettingMainFragment())
                                commit()
                            }
                        }


                    }
                }
            })
        }
    }
}