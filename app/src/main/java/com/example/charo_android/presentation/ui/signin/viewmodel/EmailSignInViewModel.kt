package com.example.charo_android.presentation.ui.signin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.data.model.request.signin.RequestSignInData
import com.example.charo_android.domain.usecase.signin.GetRemoteEmailLoginUseCase
import kotlinx.coroutines.launch

class EmailSignInViewModel(
    private val getRemoteEmailLoginUseCase: GetRemoteEmailLoginUseCase
): ViewModel() {

    var userEmail = MutableLiveData<String>()

    var nickname = MutableLiveData<String>()

    var profileImage = MutableLiveData<String>()

    var isSocial = MutableLiveData<Boolean>()




    fun getEmailSignInData(requestSignInData: RequestSignInData){
        viewModelScope.launch {
            runCatching { getRemoteEmailLoginUseCase.execute(requestSignInData) }
                .onSuccess {
                    userEmail.value = it.userEmail
                    nickname.value = it.nickname
                    profileImage.value = it.profileImage
                    isSocial.value = it.isSocial
                    Log.d("emailLogin", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("emailLogin", "서버 통신 실패")
                }
        }
    }
}