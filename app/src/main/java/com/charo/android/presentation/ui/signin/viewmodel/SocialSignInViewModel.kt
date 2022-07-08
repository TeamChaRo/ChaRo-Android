package com.charo.android.presentation.ui.signin.viewmodel

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
import timber.log.Timber

class SocialSignInViewModel(
    private val getRemoteSocialLoginData: GetRemoteSocialLoginData
) : ViewModel() {

    //카카오 로그인 성공
    var kakaoSuccess: MutableLiveData<SocialLoginData?> = MutableLiveData()

    //구글 로그인 성공
    var googleSuccess: MutableLiveData<SocialLoginData?> = MutableLiveData()

    //카카오 로그인 status
    var socialStatus: MutableLiveData<Int> = MutableLiveData()

    //구글 로그인 status
    var googleSocialStatus: MutableLiveData<Int> = MutableLiveData()
    private val _userNickName = MutableLiveData<String>()
    val userNickName: LiveData<String>
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
                    Timber.d("kakaoLogin 서버 통신 실패")
                    socialStatus.value = 500
                }
                is ResultWrapper.GenericError -> {
                    Timber.d("kakaoLogin 사용자 에러")
                    socialStatus.value = kakaoData.code ?: 0
                    Timber.d("kakaoLogin 사용자 ${kakaoData.code}")
                }
            }
        }
    }


    fun googleLoginSuccess(requestSocialData: RequestSocialData) {
        viewModelScope.launch {
            when (val googleData =
                safeApiCall(Dispatchers.IO) { getRemoteSocialLoginData.execute(requestSocialData) }) {
                is ResultWrapper.Success -> {
                    googleSuccess.value = googleData.data
                    Timber.d("googleLogin 서버 통신 성공")
                }
                is ResultWrapper.NetworkError -> {
                    Timber.d("googleLogin 서버 통신 실패")
                    googleSocialStatus.value = 500
                }
                is ResultWrapper.GenericError -> {
                    Timber.d("googleLogin 사용자 에러")
                    googleSocialStatus.value = googleData.code ?: 0
                }
            }
        }
    }
}