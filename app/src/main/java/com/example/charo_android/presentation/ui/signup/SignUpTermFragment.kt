package com.example.charo_android.presentation.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.charo_android.R
import com.example.charo_android.databinding.FragmentSignUpTermBinding
import com.example.charo_android.presentation.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class SignUpTermFragment : BaseFragment<FragmentSignUpTermBinding>(R.layout.fragment_sign_up_term) {
    private val signUpViewModel : SignUpEmailViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTermView()
    }


    private fun initTermView(){
        with(binding){
            imgSignUpTermBig.setOnClickListener {
                if (!imgSignUpTermBig.isSelected) {
                    imgSignUpTermBig.isSelected = true
                    imgSignUpTermSmall.isSelected = true
                    imgSignUpTermSmall2.isSelected = true
                    signUpViewModel.marketingEmail.value = true
                    signUpViewModel.marketingPush.value = true
                }else {
                    imgSignUpTermBig.isSelected = false
                    imgSignUpTermSmall.isSelected = false
                    imgSignUpTermSmall2.isSelected = false
                    signUpViewModel.marketingEmail.value = false
                    signUpViewModel.marketingPush.value = false
                }
            }
            imgSignUpTermSmall.setOnClickListener {
                if (!imgSignUpTermSmall.isSelected){
                    imgSignUpTermSmall.isSelected = true
                    signUpViewModel.marketingPush.value = true
                } else{
                    imgSignUpTermSmall.isSelected = false
                    signUpViewModel.marketingPush.value = false
                    imgSignUpTermBig.isSelected = false
                }
            }

            imgSignUpTermSmall2.setOnClickListener {
                if(!imgSignUpTermSmall2.isSelected){
                    imgSignUpTermSmall2.isSelected = true
                    signUpViewModel.marketingEmail.value = true
                } else{
                    imgSignUpTermSmall2.isSelected = false
                    signUpViewModel.marketingEmail.value = false
                    imgSignUpTermBig.isSelected = false
                }
            }
        }


    }


}