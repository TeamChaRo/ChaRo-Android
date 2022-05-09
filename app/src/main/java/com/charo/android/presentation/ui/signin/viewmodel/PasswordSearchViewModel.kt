package com.charo.android.presentation.ui.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charo.android.domain.usecase.signin.GetRemotePasswordSearchUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class PasswordSearchViewModel(
    private val getRemotePasswordSearchUseCase: GetRemotePasswordSearchUseCase
) : ViewModel() {


    //현재 사용자가 이메일 입력중인지
    private var _inputEmail : MutableLiveData<Boolean> = MutableLiveData()
    val inputEmail : LiveData<Boolean>
        get() = _inputEmail

    fun postInputEmail(email : Boolean){
        _inputEmail.value = email
    }

    //사용자 입력 이메일
    var userInputEmail : MutableLiveData<String> = MutableLiveData()

    //비밀번호 찾기 성공여부
    var passwordSuccess : MutableLiveData<Boolean> = MutableLiveData()

    //비밀번호 찾기
     fun getPasswordSearch(userEmail : String){
        viewModelScope.launch {
            runCatching { getRemotePasswordSearchUseCase(userEmail) }
                .onSuccess {
                    passwordSuccess.value = it.success
                    Timber.d("비밀번호 찾기 성공")
                }
                .onFailure {
                    passwordSuccess.value = false
                    Timber.d("비밀번호 찾기 실패")
                }
        }


    }
}