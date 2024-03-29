package com.charo.android.presentation.ui.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.charo.android.R
import com.charo.android.databinding.FragmentSettingPassWordUpdateBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.setting.viewmodel.SettingViewModel
import com.charo.android.presentation.util.CustomToast
import com.charo.android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber
import java.util.regex.Pattern


class SettingPasswordUpdateFragment :
    BaseFragment<FragmentSettingPassWordUpdateBinding>(R.layout.fragment_setting_pass_word_update) {
    private val settingViewModel: SettingViewModel by sharedViewModel()
    val emailPattern = """^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@!%*#?&]).{5,15}.$"""

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
        (activity as SettingActivity).binding.toolbarTitle.text = "비밀번호 수정"
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
                        textInputPasswordUpdate.error = "영문/숫자/특수문자 한개 이상 사용해주세요."
                        Timber.d("password ${Pattern.matches(emailPattern, s.toString()).toString()}")
                    } else {
                        settingViewModel.originPasswordCheck(userEmail, s.toString())
                        settingViewModel.passwordCheck.observe(viewLifecycleOwner) {
                            Timber.d("passwordCheck $it")
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
                        textInputNewPassword.error = "영문/숫자/특수문자 한개 이상 사용해주세요."
                        Timber.d("password ${Pattern.matches(emailPattern, s.toString())}")
                    }else{
                        textInputNewPassword.helperText = "확인되었습니다."
                        settingViewModel.newPasswordReconfirm.value = s.toString()
                    }
                }
            })
        }

    }
    //새 비밀번호 재확인
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
                        settingViewModel.newPasswordReconfirm.observe(viewLifecycleOwner){
                            if(it == s.toString()){
                                textInputNewPasswordReconfirm.helperText = "사용가능한 비밀번호입니다."
                                textPasswordUpdateBtn.isEnabled = true
                            }else{
                                textInputNewPasswordReconfirm.helperText = "비밀번호가 일치하지 않습니다."
                        }
                        binding.textPasswordUpdateBtn.setOnClickListener {
                            settingViewModel.newPasswordRegister(userEmail, s.toString())
                            CustomToast.createPasswordUpdateToast(requireActivity(),"비밀번호가 변경되었습니다.")?.show()
                            val transaction = activity?.supportFragmentManager?.beginTransaction()
                            transaction?.apply {
                                replace(R.id.fragment_container_setting, SettingMainFragment())
                                addToBackStack("")
                                commit()
                            }
                        }
                    }
                }
            })
        }
    }
}