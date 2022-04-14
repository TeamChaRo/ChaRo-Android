package com.charo.android.presentation.ui.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.request.signin.RequestSignInData
import com.charo.android.domain.model.signin.EmailSignInData
import com.charo.android.domain.usecase.signin.GetRemoteEmailLoginUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

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
                    Timber.d("emailLogin 서버 통신 성공")
                    Timber.d("emailLogin $it")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("emailLogin 서버 통신 실패")
                }
        }
    }
}