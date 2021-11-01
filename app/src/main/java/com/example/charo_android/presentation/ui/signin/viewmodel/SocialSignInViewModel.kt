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

    private val _success = MutableLiveData<Boolean>()
    val success : LiveData<Boolean>
        get() = _success

    private val _userEmail = MutableLiveData<String>()
    val userEmail : LiveData<String>
        get() = _userEmail



    fun kakaoLoginSuccess(requestSocialData: RequestSocialData){
        viewModelScope.launch {
            runCatching { getRemoteSocialLoginData.execute(requestSocialData) }
                .onSuccess {
                    _success.value = it.success
                    Log.d("kakao", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("kakao", "서버 통신 실패")
                }
        }
    }

    fun googleLoginSuccess(requestSocialData: RequestSocialData){
        viewModelScope.launch {
            runCatching { getRemoteSocialLoginData.execute(requestSocialData) }
                .onSuccess {
                    _success.value = it.success
                    Log.d("google", "서버 통신 성공")
                }
                .onFailure {
                    _success.value = false
                    it.printStackTrace()

                }
        }
    }
}