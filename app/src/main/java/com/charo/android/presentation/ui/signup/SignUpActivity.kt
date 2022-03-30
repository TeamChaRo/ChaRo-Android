package com.charo.android.presentation.ui.signup

import android.os.Bundle
import android.util.Log
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivitySignUpBinding
import com.example.charo_android.presentation.util.SharedInformation
import com.example.charo_android.presentation.util.changeFragment

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val signUpViewModel: SignUpEmailViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSignUpEmailFragment()
        changeGoogleSignUpFragment()
    }


    fun initSignUpEmailFragment() {
        if(SharedInformation.getSignUp(this) == 0){
            changeFragment(R.id.fragment_container_email, SignUpEmailFragment())
        }


    }

    //구글 회원가입시에
    fun changeGoogleSignUpFragment(){

        if(SharedInformation.getSignUp(this) == 1){
            signUpViewModel.userEmail.value = intent.getStringExtra("googleSignUpEmail")
            signUpViewModel.googleProfileImage.value = intent.getStringExtra("googleProfileImage")
            changeFragment(R.id.fragment_container_email, SignUpTermFragment())
            Log.d("google", "왜 니가 되는 거냐")
        }


        if(SharedInformation.getSignUp(this) == 2){
            signUpViewModel.userEmail.value = intent.getStringExtra("kakaoSignUpEmail")

            changeFragment(R.id.fragment_container_email, SignUpProfileFragment())
        }
    }

}