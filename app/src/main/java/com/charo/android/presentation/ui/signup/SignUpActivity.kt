package com.charo.android.presentation.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.blue
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivitySignUpBinding
import com.example.charo_android.domain.model.signup.Email
import com.example.charo_android.presentation.base.BaseActivity
import com.example.charo_android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import com.example.charo_android.presentation.util.SharedInformation
import com.example.charo_android.presentation.util.changeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Observer
import kotlin.math.sign

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