package com.example.charo_android.presentation.ui.signin.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.data.model.request.signin.RequestSocialData
import com.example.charo_android.domain.usecase.signin.GetRemoteSocialLoginData
import com.example.charo_android.presentation.ui.signup.SignUpActivity
import kotlinx.coroutines.launch

class SocialSignInViewModel(
    private val getRemoteSocialLoginData: GetRemoteSocialLoginData
) : ViewModel() {

    //카카오 로그인 성공
    var kakaoSuccess : MutableLiveData<Boolean> = MutableLiveData()

    //구글 로그인 성공
    var googleSuccess : MutableLiveData<Boolean> = MutableLiveData()
    private val _userEmail = MutableLiveData<String>()
    val userEmail : LiveData<String>
        get() = _userEmail


    //카카오 로그인 성공
    fun kakaoLoginSuccess(requestSocialData: RequestSocialData){
        viewModelScope.launch {
            runCatching { getRemoteSocialLoginData.execute(requestSocialData) }
                .onSuccess {
                    kakaoSuccess.value = it.success
                    Log.d("kakaoSuccess", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("kakaoSuccess", "서버 통신 실패")
                }
        }
    }

    fun googleLoginSuccess(requestSocialData: RequestSocialData){
        viewModelScope.launch {
            runCatching { getRemoteSocialLoginData.execute(requestSocialData) }
                .onSuccess {
                    googleSuccess.value = it.success
                    Log.d("google", "서버 통신 성공")
                }
                .onFailure {
                    googleSuccess.value = false
                    it.printStackTrace()

                }
        }
    }
}