package com.example.charo_android.presentation.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSignUpProfileBinding
import com.example.charo_android.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sign_up_profile.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.regex.Pattern


class SignUpProfileFragment : BaseFragment<FragmentSignUpProfileBinding>(R.layout.fragment_sign_up_profile) {
    private val signUpViewModel: SignUpEmailViewModel by sharedViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkNickName()
    }



    private fun checkNickName(){
        val nickNamePattern = "^[가-힣ㄱ-ㅎ]{0,5}$"
        with(binding){
            img_sign_up_profile_delete_button.setOnClickListener {
                etSignUpNickname.setText("")
            }

            etSignUpNickname.addTextChangedListener(object: TextWatcher{
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
                    if(s.toString().length > 5){
                        textInputNickname.error = "5자 이내로 작성해주세요"
                    } else if(!Pattern.matches(nickNamePattern,s.toString())){
                        textInputNickname.error = "한글만 사용해주세요"
                    } else {
                        signUpViewModel.nickNameCheck(s.toString())
                        signUpViewModel.nickNameCheck.observe(viewLifecycleOwner){
                            if(!it){
                                textInputNickname.error = "중복되는 닉네임이 존재합니다"
                            } else{
                                textInputNickname.error = null
                                textInputNickname.isErrorEnabled = false
                                textInputNickname.isHelperTextEnabled = true
                                textInputNickname.helperText = "사용 가능한 닉네임입니다"
                            }
                        }
                    }
                }
            })
        }




    }
}