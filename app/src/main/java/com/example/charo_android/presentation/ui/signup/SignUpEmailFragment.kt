package com.example.charo_android.presentation.ui.signup


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSignUpEmailBinding
import com.example.charo_android.domain.model.signup.Email
import com.example.charo_android.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpEmailFragment : BaseFragment<FragmentSignUpEmailBinding>(R.layout.fragment_sign_up_email) {
    private var pass = false
    private val signUpViewModel: SignUpEmailViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeSuccess()
    }

    private fun initView() {
        val naver = "@naver.com"
        val gmail = "@gmail.com"
        with(binding) {
            clEmailNum.isVisible = false
            imgDeleteButton.setOnClickListener {
                etSignUpBlank.setText("")
            }
            etSignUpBlank.addTextChangedListener(object : TextWatcher {
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
                    if (!s.toString().contains(naver) and !s.toString().contains(gmail)) {
                        textInputSignUp.error = "사용 불가능한 이메일 형식입니다."
                        pass = false
                    } else {
                        pass = true
                        checkEmail(Email(s.toString()))
                    }
                }
            })
        }
    }

    private fun observeSuccess() {
        signUpViewModel.success.observe(this) {
            with(binding) {
                if (it) {
                    textInputSignUp.error = null
                    textInputSignUp.isErrorEnabled = false
                    textInputSignUp.isHelperTextEnabled = true
                    textInputSignUp.helperText = "사용 가능한 이메일 형식입니다."

                    imgSignUpNext.setOnClickListener {
                        clEmailNum.isVisible = true
                    }
                }
                else {
                    if(pass)
                        textInputSignUp.error = "중복된 이메일 형식입니다."
                }
            }
        }

    }

    private fun checkEmail(email: Email) {
        return signUpViewModel.emailCheck(email)
    }
}