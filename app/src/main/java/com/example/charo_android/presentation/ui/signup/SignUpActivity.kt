package com.example.charo_android.presentation.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.graphics.blue
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import com.example.charo_android.R
import com.example.charo_android.databinding.ActivitySignUpBinding
import com.example.charo_android.domain.model.signup.Email
import com.example.charo_android.presentation.base.BaseActivity
import com.example.charo_android.presentation.util.changeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Observer
import kotlin.math.sign

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSignUpEmailFragment()
    }


    fun initSignUpEmailFragment(){
        changeFragment(R.id.fragment_container_email, SignUpProfileFragment())

    }





}