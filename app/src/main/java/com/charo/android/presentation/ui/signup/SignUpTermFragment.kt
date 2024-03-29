package com.charo.android.presentation.ui.signup

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.charo.android.R
import com.charo.android.databinding.FragmentSignUpTermBinding
import com.charo.android.presentation.base.BaseFragment
import com.charo.android.presentation.ui.main.MainActivity
import com.charo.android.presentation.ui.signup.viewmodel.SignUpEmailViewModel
import com.charo.android.presentation.util.SharedInformation
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class SignUpTermFragment : BaseFragment<FragmentSignUpTermBinding>(R.layout.fragment_sign_up_term) {
    private val signUpViewModel : SignUpEmailViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTermView()
        signUpComplete()
        googleSignUp()
        kakaoSignUp()
        kakaoSignUpSuccess()
        completeGoogle()
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

    //일반 회원가입 시에
    fun signUpComplete(){
        if (SharedInformation.getSignUp(requireActivity()) == 0){
            binding.imgSignUpTermNext.setOnClickListener {
                with(signUpViewModel){
                    Timber.d("signUp ${userEmail.value.toString()} ${password.value.toString()} ${nickName.value.toString()}")
                    signUpRegister(
                        profileImage.value
                            ?: Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(R.drawable.ic_profile) + '/' + resources.getResourceTypeName(R.drawable.ic_profile) + '/' + resources.getResourceEntryName(R.drawable.ic_profile)) ,
                        userEmail.value.toString(),
                        password.value.toString(),
                        nickName.value.toString(),
                        pushAgree.value ?: false,
                        emailAgree.value ?: false,
                        requireActivity()
                    )
                    registerSuccess.observe(viewLifecycleOwner){
                        if(it){
                            SharedInformation.saveSocialId(requireActivity(), "3")
                            SharedInformation.setPassword(requireActivity(), signUpViewModel.password.value.toString())
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
    //구글 로그인 회원가입 완료
    fun completeGoogle(){
        signUpViewModel.googleRegisterSuccess.observe(viewLifecycleOwner){
            if(it.success){
                SharedInformation.saveSocialId(requireActivity(), "2")
                SharedInformation.setEmail(requireActivity(), signUpViewModel.userEmail.value.toString())
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
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
                SharedInformation.setEmail(requireActivity(), signUpViewModel.userEmail.value.toString())
                SharedInformation.saveSocialId(requireActivity(), "1")
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }
    }

}