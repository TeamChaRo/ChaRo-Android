package com.charo.android.presentation.ui.signup

import android.os.Bundle
import android.view.MenuItem
import com.charo.android.R
import com.charo.android.databinding.ActivitySignUpBinding
import com.charo.android.presentation.base.BaseActivity
import com.charo.android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import com.charo.android.presentation.util.CustomDialog
import com.charo.android.presentation.util.SharedInformation
import com.charo.android.presentation.util.changeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val signUpViewModel: SignUpEmailViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()

        initSignUpEmailFragment()
        changeGoogleSignUpFragment()
        observeData()
    }

    private fun initToolbar() {
        val toolbar = binding.toolbarSignUp
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_1)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun initSignUpEmailFragment() {
        if (SharedInformation.getSignUp(this) == 0) {
            changeFragment(R.id.fragment_container_email, SignUpEmailFragment())
        }
    }

    //구글 회원가입시에
    fun changeGoogleSignUpFragment() {

        if (SharedInformation.getSignUp(this) == 1) {
            signUpViewModel.userEmail.value = intent.getStringExtra("googleSignUpEmail")
            signUpViewModel.googleProfileImage.value = intent.getStringExtra("googleProfileImage")
            changeFragment(R.id.fragment_container_email, SignUpTermFragment())
            Timber.d("google 왜 니가 되는 거냐")
        }

        //카카오 회원가입시에
        if (SharedInformation.getSignUp(this) == 2) {
            signUpViewModel.userEmail.value = intent.getStringExtra("kakaoSignUpEmail")
            changeFragment(R.id.fragment_container_email, SignUpProfileFragment())
        }
    }

    private fun observeData() {
        signUpViewModel.serverErrorOccurred.observe(this) {
            CustomDialog(this).showServerErrorDialog(this).show()
        }
    }
}