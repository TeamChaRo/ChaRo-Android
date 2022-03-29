package com.charo.android.presentation.ui.signin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.data.model.request.signin.RequestSignInData
import com.example.charo_android.domain.model.signin.EmailSignInData
import com.example.charo_android.domain.usecase.signin.GetRemoteEmailLoginUseCase
import kotlinx.coroutines.launch

class EmailSignInViewModel(
    private val getRemoteEmailLoginUseCase: GetRemoteEmailLoginUseCase
): ViewModel() {

    private val _emailSignInData = MutableLiveData<EmailSignInData>()
    var emailSignInData : LiveData<EmailSignInData> = _emailSignInData






    fun getEmailSignInData(requestSignInData: RequestSignInData){
        viewModelScope.launch {
            runCatching { getRemoteEmailLoginUseCase.execute(requestSignInData) }
                .onSuccess {
                   _emailSignInData.value = it
                    Log.d("emailLogin", "서버 통신 성공")
                    Log.d("emailLogin", it.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("emailLogin", "서버 통신 실패")
                }
        }
    }
}