package com.example.charo_android.presentation.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charo_android.data.model.request.RequestCertificationData
import com.example.charo_android.domain.usecase.signup.GetRemoteSignUpEmailCertificationUseCase
import com.example.charo_android.domain.usecase.signup.GetRemoteSignUpEmailCheckUseCase
import com.example.charo_android.domain.usecase.signup.GetRemoteSignUpNickNameCheckUseCase
import kotlinx.coroutines.launch

class SignUpEmailViewModel(
    private val getRemoteSignUpEmailCheckUseCase: GetRemoteSignUpEmailCheckUseCase,
    private val getRemoteSignUpEmailCertificationUseCase: GetRemoteSignUpEmailCertificationUseCase,
    private val getRemoteSignUpNickNameCheckUseCase: GetRemoteSignUpNickNameCheckUseCase
) : ViewModel() {

    private val _success = MutableLiveData<Boolean>(false)
    val success: LiveData<Boolean>
        get() = _success

    private val _data = MutableLiveData<String>()
    val data: LiveData<String>
        get() = _data

    private val _nickNameCheck = MutableLiveData<Boolean>()
    val nickNameCheck : LiveData<Boolean>
        get() = _nickNameCheck

    var profileImage = MutableLiveData<String>()

    var marketingPush = MutableLiveData<Boolean>()

    var marketingEmail = MutableLiveData<Boolean>()


    fun emailCheck(email: String) {
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

                }
        }
    }

    fun emailCertification(userEmail: String){
        viewModelScope.launch {
            runCatching { getRemoteSignUpEmailCertificationUseCase.execute(userEmail) }
                .onSuccess {
                    _data.value = it
                    Log.d("signUps", "서버 통신 성공!")
                    Log.d("signUp", it.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("signUps", "서버 통신 실패!")

                }
        }
    }

    fun nickNameCheck(nickname: String){
        viewModelScope.launch {
            kotlin.runCatching { getRemoteSignUpNickNameCheckUseCase.execute(nickname) }
                .onSuccess {
                    _nickNameCheck.value = it
                    Log.d("nickname", "서버 통신 성공!")
                    Log.d("nickname", it.toString())
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("nickname", "서버 통신 실패!")
                }
        }

    }
}