package com.example.charo_android.presentation.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.domain.model.signup.Email
import com.example.charo_android.domain.model.signup.EmailCheck
import com.example.charo_android.domain.usecase.GetRemoteSignUpEmailCheckUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val getRemoteSignUpEmailCheckUseCase: GetRemoteSignUpEmailCheckUseCase
) : ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    fun emailCheck(email: Email) {
        viewModelScope.launch {
            runCatching { getRemoteSignUpEmailCheckUseCase.execute(email) }

                .onSuccess {
                    _success.value = it
                    Log.d("signUp", "서버 통신 성공!")
                    Log.d("signUp", it.toString())
                }

                .onFailure {
                    it.printStackTrace()
                    Log.d("signUp", "서버 통신 실패")
                    Log.d("signUp", it.toString())
                }
        }
    }
}