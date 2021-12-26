package com.example.charo_android.presentation.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSignUpTermBinding
import com.example.charo_android.presentation.base.BaseFragment
import com.example.charo_android.presentation.ui.main.MainActivity
import com.example.charo_android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import com.example.charo_android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SignUpTermFragment : BaseFragment<FragmentSignUpTermBinding>(R.layout.fragment_sign_up_term) {
    private val signUpViewModel : SignUpEmailViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTermView()
        signUpComplete()
        googleSignUp()
        kakaoSignUp()
        kakaoSignUpSuccess()
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
        if (SharedInformation.getSignUp(requireActivity()) == 0){
            binding.imgSignUpTermNext.setOnClickListener {
                with(signUpViewModel){
                    Log.d("signUp", userEmail.value.toString() + password.value.toString() +
                            nickName.value.toString())
                    signUpRegister(
                        profileImage.value!!,
                        userEmail.value.toString(),
                        password.value.toString(),
                        nickName.value.toString(),
                        pushAgree.value ?: false,
                        emailAgree.value ?: false,
                        requireActivity()
                    )
                    registerSuccess.observe(viewLifecycleOwner){
                        if(it){
                            SharedInformation.setEmail(requireActivity(), signUpViewModel.userEmail.value.toString())
                            val intent = Intent(requireActivity(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    }
                }
        }


        }
    }
    //구글 로그인으로 회원가입시에
    fun googleSignUp(){
        if(SharedInformation.getSignUp(requireActivity()) == 1){
            binding.imgSignUpTermNext.setOnClickListener {
                with(signUpViewModel){
                    signUpGoogle(
                        userEmail.value.toString(),
                        googleProfileImage.value.toString(),
                        pushAgree.value ?: false,
                        emailAgree.value ?: false
                    )
                }
            }
        }
    }
    //카카오 회원가입
    fun kakaoSignUp(){
        if(SharedInformation.getSignUp(requireActivity()) == 2){
            binding.imgSignUpTermNext.setOnClickListener {
                with(signUpViewModel){
                    signUpKaKao(
                        userEmail.value.toString(),
                        profileImage.value.toString(),
                        nickName.value.toString(),
                        pushAgree.value ?: false,
                        emailAgree.value ?: false
                    )
                }
            }
        }
    }

    //카카오 회원가입 성공
    private fun kakaoSignUpSuccess(){
        signUpViewModel.kakaoRegisterSuccess.observe(viewLifecycleOwner){
            if(it.success){
                SharedInformation.saveSocialId(requireActivity(), "1")
                SharedInformation.setKaKaoSignUp(requireActivity(), 2)
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }
    }

}