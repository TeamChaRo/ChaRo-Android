package com.charo.android.presentation.ui.signin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.request.signin.RequestSocialData
import com.charo.android.domain.model.signin.SocialLoginData
import com.charo.android.domain.usecase.signin.GetRemoteSocialLoginData
import com.charo.android.presentation.util.ResultWrapper
import com.charo.android.presentation.util.safeApiCall

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SocialSignInViewModel(
    private val getRemoteSocialLoginData: GetRemoteSocialLoginData
) : ViewModel() {

    //카카오 로그인 성공
    var kakaoSuccess : MutableLiveData<SocialLoginData?> = MutableLiveData()

    //구글 로그인 성공
    var googleSuccess : MutableLiveData<SocialLoginData?> = MutableLiveData()

    //카카오 로그인 status
    var socialStatus : MutableLiveData<Int> = MutableLiveData()

    //구글 로그인 status
    var googleSocialStatus : MutableLiveData<Int> = MutableLiveData()
    private val _userNickName = MutableLiveData<String>()
    val userNickName : LiveData<String>
        get() = _userNickName

    //카카오 로그인 성공
    fun kakaoLoginSuccess(requestSocialData: RequestSocialData) {
        viewModelScope.launch {
            when (val kakaoData =
                safeApiCall(Dispatchers.IO) { getRemoteSocialLoginData.execute(requestSocialData) }) {
                is ResultWrapper.Success -> {
                    kakaoSuccess.value = kakaoData.data
                }
                is ResultWrapper.NetworkError -> {
                    Log.d("kakaoSuccess", "서버 통신 실패")
                }
                is ResultWrapper.GenericError -> {
                    Log.d("kakaoSuccess", "사용자 에러")
                    socialStatus.value = kakaoData.code ?: 0
                }
            }
        }
    }


    fun     googleLoginSuccess(requestSocialData: RequestSocialData){
        viewModelScope.launch {
            when (val googleData =
                safeApiCall(Dispatchers.IO) {getRemoteSocialLoginData.execute(requestSocialData) }){
                is ResultWrapper.Success -> {
                    googleSuccess.value = googleData.data
                    Log.d("google", "서버 통신 성공")
                }
                is ResultWrapper.NetworkError -> {
                    Log.d("google", "서버 통신 실패")
                }
                is ResultWrapper.GenericError -> {
                    Log.d("google", "사용자 에러")
                    googleSocialStatus.value = googleData.code ?: 0
                }

            }
        }
    }
}