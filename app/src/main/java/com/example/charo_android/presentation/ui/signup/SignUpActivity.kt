package com.example.charo_android.presentation.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        changeFragment(R.id.fragment_container_email, SignUpEmailFragment())

    }

    //구글 회원가입시에
    fun changeGoogleSignUpFragment(){
        val googleNum = intent.getIntExtra("googleSignUp", 999)

        signUpViewModel.userEmail.value = intent.getStringExtra("googleSignUpEmail")
        signUpViewModel.socialLoginNum.value = googleNum
        signUpViewModel.googleProfileImage.value = intent.getStringExtra("googleProfileImage")
        if(googleNum == 1){
            changeFragment(R.id.fragment_container_email, SignUpTermFragment())
        }
    }

}