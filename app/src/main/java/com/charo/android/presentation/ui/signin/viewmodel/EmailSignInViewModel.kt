package com.charo.android.presentation.ui.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.charo.android.data.model.request.signin.RequestSignInData
import com.charo.android.domain.model.signin.EmailSignInData
import com.charo.android.domain.usecase.signin.GetRemoteEmailLoginUseCase
import com.charo.android.presentation.base.BaseViewModel
import com.charo.android.presentation.util.ResultWrapper
import com.charo.android.presentation.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class EmailSignInViewModel(
    private val getRemoteEmailLoginUseCase: GetRemoteEmailLoginUseCase
) : BaseViewModel() {

    private val _emailSignInData = MutableLiveData<EmailSignInData>()
    var emailSignInData: LiveData<EmailSignInData> = _emailSignInData

    var emailSignInStatus = MutableLiveData<Int>()
    var emailSignInErrorMsg = MutableLiveData<String>()

    fun getEmailSignInData(requestSignInData: RequestSignInData) {
        viewModelScope.launch {
            when (val postEmailSignIn =
                safeApiCall(Dispatchers.IO) { getRemoteEmailLoginUseCase(requestSignInData) }) {
                is ResultWrapper.Success -> {
                    _emailSignInData.value = postEmailSignIn.data!!
                    Timber.d("emailLogin 서버 통신 성공")
                    emailSignInStatus.value = 200
                }
                is ResultWrapper.NetworkError -> {
                    emailSignInStatus.value = 500
                }
                is ResultWrapper.GenericError -> {
                    postEmailSignIn.code?.let {
                        if (it / 100 == 5) {
                            setServerErrorFlag(true)
                        }
                    }
                    emailSignInErrorMsg.value = postEmailSignIn.msg.toString()
                    emailSignInStatus.value = 404
                }
            }
        }
    }
}

