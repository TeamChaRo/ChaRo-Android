package com.example.charo_android.presentation.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivitySignUpBinding
import com.example.charo_android.domain.model.signup.Email
import com.example.charo_android.presentation.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Observer

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    private val signUpViewModel: SignUpViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

    }


    private fun initView() {
        val naver = "@naver.com"
        val gmail = "@gmail.com"
        with(binding) {
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
                        checkEmail(Email("and@naver.com"))
                    } else if(!checkEmail(Email(s.toString())).toString().toBoolean()){
                        textInputSignUp.error = "중복된 이메일 형식입니다."
                    } else{
                        textInputSignUp.error = null
                    }
                }
            })
        }
    }

    private fun checkEmail(email: Email){
        return signUpViewModel.emailCheck(email)

    }

}