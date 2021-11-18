package com.example.charo_android.presentation.ui.signup

import android.os.Bundle
import android.view.View
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSignUpTermBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SignUpTermFragment : BaseFragment<FragmentSignUpTermBinding>(R.layout.fragment_sign_up_term) {
    private val signUpViewModel : SignUpEmailViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTermView()
        signUpComplete()
    }


    private fun initTermView(){
        with(binding){
            imgSignUpTermBig.setOnClickListener {
                if (!imgSignUpTermBig.isSelected) {
                    imgSignUpTermBig.isSelected = true
                    imgSignUpTermSmall.isSelected = true
                    imgSignUpTermSmall2.isSelected = true
                    signUpViewModel.emailAgree.value = true
                    signUpViewModel.pushAgree.value = true
                }else {
                    imgSignUpTermBig.isSelected = false
                    imgSignUpTermSmall.isSelected = false
                    imgSignUpTermSmall2.isSelected = false
                    signUpViewModel.emailAgree.value = false
                    signUpViewModel.pushAgree.value = false
                }
            }
            imgSignUpTermSmall.setOnClickListener {
                if (!imgSignUpTermSmall.isSelected){
                    imgSignUpTermSmall.isSelected = true
                    signUpViewModel.pushAgree.value = true
                } else{
                    imgSignUpTermSmall.isSelected = false
                    signUpViewModel.pushAgree.value = false
                    imgSignUpTermBig.isSelected = false
                }
            }

            imgSignUpTermSmall2.setOnClickListener {
                if(!imgSignUpTermSmall2.isSelected){
                    imgSignUpTermSmall2.isSelected = true
                    signUpViewModel.emailAgree.value = true
                } else{
                    imgSignUpTermSmall2.isSelected = false
                    signUpViewModel.emailAgree.value = false
                    imgSignUpTermBig.isSelected = false
                }
            }
        }


    }

    fun signUpComplete(){
        binding.imgSignUpTermNext.setOnClickListener {
                with(signUpViewModel){
                    signUpRegister(
                        profileImage.value!!,
                        userEmail.value.toString(),
                        password.value.toString(),
                        nickName.value.toString(),
                        pushAgree.value ?: false,
                        emailAgree.value ?: false,
                        requireActivity()
                    )
                }

        }
    }


}